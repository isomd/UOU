# Tml-DDD 模板使用指南

> 目标：帮助模板使用者用“高内聚、低耦合、可演进”的方式组织业务代码。
>
> 这份 Guide 不追求教条式 DDD。你可以把它理解为：一套更贴近日常开发的分层约定 + 一些实战决策规则。

---

## 0. TL;DR：代码放哪？

![image](https://geniusnoteimages.oss-cn-hangzhou.aliyuncs.com/20260109013546787.png)

六边形架构（Ports & Adapters）的直觉是：把业务核心放在中间，把 HTTP/MQ/DB/第三方等都当成可替换的适配器。

- 高内聚：同一个用例相关的代码尽量放在一起
- 低耦合：把容易变化的点（协议、存储、三方）隔离在端口与实现之后

越往内越“纯业务”（模型与规则），越往外越“技术/协议”（HTTP、MQ、Redis、DB、三方调用等）。

- 对外 req/resp、错误码、OpenAPI/Proto：放 `tml-api`
- HTTP/RPC/MQ/Job 接入、协议解析、统一异常映射、Req/Resp 转换：放 `tml-adapter`
- 用例编排（port.in/port.out）、场景流程（process）、内部业务模型（model）：放 `tml-service`
- 业务核心模型与规则（聚合、值对象、领域服务、领域事件）：放 `tml-domain`
- DB/Redis/第三方接口/消息生产者等技术实现：放 `tml-infrastructure`
- 给其它服务用的 SDK：放 `tml-client`

一句话：越往内越“纯业务”，越往外越“技术/协议”。

---

## 1. 模块总览：一个服务长什么样

本项目最外层 Maven 有两个模块：

- `tml-app`：应用启动入口（Bootstrap）
- `tml-standard`：一个完整服务（或一个限界上下文）的实现骨架

`tml-standard` 下的模块结构（一个服务的标准形状）：

- `tml-api`：对外服务契约（req/resp、错误码、OpenAPI/Proto）
- `tml-adapter`：适配层（HTTP/RPC/MQ/Job），负责接入协议与数据转换
- `tml-service`：应用层（UseCase），负责用例/流程编排与端口定义
- `tml-domain`：领域层（业务核心），放领域模型与规则
- `tml-infrastructure`：基础设施实现（DB/缓存/第三方/消息生产者等）
- `tml-client`：对外 SDK（给其它服务或调用方用）

依赖方向（简化）：

```text
tml-app
  → tml-adapter
      → tml-service → tml-domain
      → tml-api
tml-infrastructure → tml-service, tml-domain
tml-client → tml-api
```

你会看到一个刻意的设计：

- `tml-service`（应用层）不依赖 `tml-infrastructure`（技术实现）
- 需要访问 DB/Redis/第三方时，通过 `port.out` 接口隔离变化

---

## 2. 两套“契约”：对外契约 vs 服务内契约

这套模板建议你把数据模型分成两套：

- 对外契约（API Contract）：给调用方看的，放 `tml-api`
  - `PayOrderReq` / `PayOrderResp`
  - `ErrorCode` / `ApiErrorResponse`
- 服务内契约（Internal Contract）：给应用层用例看的，放 `tml-service`
  - `PayOrderModel`
  - `CreateOrderModel`

这样做的目的很简单：

- 对外契约经常受网关、前端、协议格式影响，变化更频繁
- 服务内模型更贴近业务语义，应该更稳定

典型链路（推荐）：

```text
HTTP/Proto/MQ
  → tml-adapter（协议解析）
      → tml-api req/resp（对外契约）
          → tml-adapter delegate（req→model）
              → tml-service port.in（用例）
```

---

## 3. `tml-app`：启动与装配模块

**这个模块干什么？**

- 放应用启动入口（main / Spring Boot 启动类）
- 放服务级装配（把 adapter/service/domain/infrastructure 组装成可运行的服务）
- 放启动时初始化入口（比如启动后做一次预热），但不要在这里写业务规则

**适合放哪些类？**

- `Application` / `App`（启动类）
- `StartupRunner`（启动后执行的 runner，内部调用应用层用例）

**不要放什么？**

- Controller、Repository、领域模型
- 复杂业务逻辑

---

## 4. `tml-api`：服务契约模块

**定位**：给“服务调用者”（前端、网关、其它微服务）提供稳定的调用契约。

当前模板已补齐推荐包：

```text
io.github.timemachinelab.api
  ├─ req
  ├─ resp
  ├─ enums
  ├─ error
  └─ contract
       ├─ openapi
       └─ proto
```

**包说明 + 放什么**

- `req`：对外请求
  - 例：`PayOrderReq`、`CreateOrderReq`
- `resp`：对外响应
  - 例：`PayOrderResp`、`OrderDetailResp`
- `enums`：公共枚举
  - 例：`OrderStatus`、`PayChannel`
- `error`：错误码与统一错误响应
  - 例：`ErrorCode`、`ApiErrorResponse`
- `contract/openapi`：OpenAPI 文件（可选）
  - 例：`payment-api.yaml`
- `contract/proto`：Proto 文件（可选）
  - 例：`payment-service.proto`

**一个直观例子**

当你要新增一个接口“支付订单”：

- 在 `req/resp` 新增：`PayOrderReq` / `PayOrderResp`
- 在 `error` 补充：`PAYMENT_ORDER_NOT_FOUND` 这类错误码
- 如果你用 OpenAPI：在 `contract/openapi` 增量维护接口文档

**不要放什么**

- 任何 Repository、ApplicationService、Domain 对象
- 与框架强绑定的类（Controller、Filter 等）

---

## 5. `tml-adapter`：适配层模块（HTTP/RPC/MQ/Job）

**定位**：处理“世界如何调用你”。把外部协议翻译成内部用例调用。

当前模板已补齐推荐包：

```text
io.github.timemachinelab.adapter
  ├─ web
  │    ├─ controller
  │    ├─ delegate
  │    ├─ interceptor
  │    └─ handler
  ├─ rpc
  ├─ mq
  │    ├─ listener
  │    └─ delegate
  └─ scheduler
       └─ job
```

**包说明 + 放什么**

- `web/controller`：HTTP Controller（只做协议接入）
  - 例：`PaymentController`、`OrderController`
- `web/delegate`：API req/resp → 内部业务模型（model）的转换与调用
  - 例：`PaymentWebDelegate`
- `web/interceptor`：HTTP 拦截器/Filter
  - 例：鉴权、traceId 透传、日志上下文
- `web/handler`：统一异常映射
  - 例：`GlobalExceptionHandler`：把内部异常映射为 `ApiErrorResponse`
- `mq/listener`：MQ 消费入口（反序列化、幂等入口、调用用例）
  - 例：`PaymentTimeoutListener`
- `mq/delegate`：消息 req/resp → 内部业务模型（model）的转换与调用
  - 例：`PaymentMessageDelegate`
- `scheduler/job`：定时任务入口（只负责触发）
  - 例：`CloseExpiredOrdersJob`
- `rpc`：gRPC/Dubbo 等入口适配（可选）

**适配层最重要的两段“翻译”**

1) 协议 → API req（`tml-api`）

- HTTP JSON → `PayOrderReq`
- MQ 二进制 → `PayOrderReq`
- Protobuf → `PayOrderReq`

2) API req → 内部模型（`tml-service`）

- `PayOrderReq` → `PayOrderModel`
- `PayOrderResp` ← （由内部结果映射而来）

你之前担心的点（应用层被迫使用 adapter 的数据契约），在这里会被彻底规避：

- 应用层 `port.in` 的入参永远是 `*Model` 这类内部模型
- req/resp 的存在感只停留在 adapter 和 api

---

## 6. `tml-service`：应用层模块（UseCase + Port）

**定位**：用例编排层。

- 定义系统“能做什么”：`port.in`
- 定义对外依赖“需要什么能力”：`port.out`
- 编排领域对象 + 调用 `port.out` 完成一个用例

当前模板已补齐推荐包：

```text
io.github.timemachinelab.service
  ├─ port
  │    ├─ in
  │    └─ out
  ├─ application
  ├─ process
  └─ model
```

**包说明 + 放什么**

- `port/in`：入站端口（用例接口）
  - 例：`PayOrderUseCase#pay(PayOrderModel)`
- `port/out`：出站端口（抽象外部能力）
  - 例：`PaymentGatewayPort`、`OrderRepositoryPort`、`PaymentRecordRepositoryPort`
- `application`：用例实现（ApplicationService），实现 `port.in`
  - 例：`PaymentApplicationService implements PayOrderUseCase`
- `process`：场景编排（跨用例/跨子域），不直接做底层技术
  - 例：`PayOrderProcessService`：编排“创建订单 → 决策 → 支付 → 记流水 → 调三方”
- `model`：服务内业务模型（用例入参/中间态等），不是对外契约
  - 例：`PayOrderModel`、`CreateOrderModel`

**port.in 什么时候写？**

- 你只要确认“有一个用例要对外提供”，就优先写 `port.in` 接口，并在 `application` 里实现
- adapter 只依赖 `port.in`，避免出现 Controller 直接依赖实现类

---

## 7. `tml-domain`：领域层模块（业务核心）

**定位**：放业务核心概念与规则。

当前模板已补齐按子域拆分的包结构（并保留一个 `common` 做共享）：

```text
io.github.timemachinelab.domain
  ├─ common
  ├─ order
  │    ├─ model
  │    ├─ service
  │    └─ event
  └─ payment
       ├─ model
       ├─ service
       └─ event
```

**包说明 + 放什么**

- `order/model`：订单聚合、实体、值对象
  - 例：`Order`、`OrderItem`、`Money`
- `order/service`：订单领域服务（纯业务规则）
  - 例：`OrderDomainService`
- `order/event`：领域事件（可选）
  - 例：`OrderCreatedEvent`
- `payment/*`：同理
- `common`：跨子域共享但稳定的概念
  - 例：通用值对象 `Money`、通用异常基类（如果你选择放领域异常）

**不要放什么**

- HTTP、MQ、JSON、DB、Redis 等技术细节

---

## 8. `tml-infrastructure`：基础设施模块（实现 port.out）

**定位**：所有技术实现都放这里，专门去实现应用层定义的 `port.out`。

当前模板已补齐推荐包：

```text
io.github.timemachinelab.infrastructure
  ├─ persistence
  │    ├─ entity
  │    └─ repository
  ├─ cache
  ├─ external
  ├─ mq
  │    └─ producer
  └─ config
```

**包说明 + 放什么**

- `persistence/entity`：数据库映射对象
  - 例：`OrderEntity`、`PaymentRecordEntity`
- `persistence/repository`：仓储实现
  - 例：`JpaOrderRepository implements OrderRepositoryPort`
- `cache`：缓存实现（Redis/本地缓存）
  - 例：`RedisOrderCache implements OrderCachePort`
- `external`：第三方接口调用实现
  - 例：`HttpPaymentGatewayAdapter implements PaymentGatewayPort`
- `mq/producer`：消息生产者实现
  - 例：`KafkaPaymentEventPublisher implements PaymentEventPublisherPort`
- `config`：数据源、事务、Redis/MQ 等技术配置

**小框架（消费者/生产者框架、缓存框架）放哪？**

- 如果只服务于当前服务：放 `tml-infrastructure` 里（比如 `infrastructure/cache` 下）
- 如果要复用给多个服务：建议抽成独立 Maven 模块，然后各服务的 `*-infrastructure` 依赖它

---

## 9. `tml-client`：对外 SDK 模块

**定位**：给其它服务/调用方提供“像本地方法一样调用”的 SDK。

当前模板已补齐推荐包：

```text
io.github.timemachinelab.client
  ├─ api
  └─ internal
```

**包说明 + 放什么**

- `api`：对外暴露的 Client
  - 例：`PaymentServiceClient`、`OrderServiceClient`
- `internal`：内部实现（HTTP/gRPC 调用、鉴权、重试、熔断等）

建议 SDK 的方法签名直接复用 `tml-api` 的 req/resp：

- `PaymentServiceClient.pay(PayOrderReq): PayOrderResp`

---

## 10. 一个完整例子：支付订单（从外到内怎么走）

假设你的业务流程是：

1) 创建订单（订单子域）
2) 基于订单信息决定是否继续支付
3) 执行支付业务（支付子域）
4) 存储支付流水
5) 调用第三方接口进行支付

推荐的落层方式：

- `tml-api`
  - `req.PayOrderReq`、`resp.PayOrderResp`
- `tml-adapter`
  - `web/controller.PaymentController`：接 HTTP
  - `web/delegate.PaymentWebDelegate`：`PayOrderReq -> PayOrderModel`，调用 `PayOrderUseCase`
- `tml-service`
  - `process.PayOrderProcessService`：编排“创建订单 → 决策 → 支付”
  - `port/in.CreateOrderUseCase`、`port/in.PayOrderUseCase`
  - `application.OrderApplicationService`、`application.PaymentApplicationService`
  - `port/out.OrderRepositoryPort`、`port/out.PaymentRecordRepositoryPort`、`port/out.PaymentGatewayPort`
- `tml-domain`
  - `order/model.Order`、`payment/model.Payment`、`payment/model.PaymentRecord`
- `tml-infrastructure`
  - `persistence/repository.JpaOrderRepository`（实现 `OrderRepositoryPort`）
  - `persistence/repository.JpaPaymentRecordRepository`（实现 `PaymentRecordRepositoryPort`）
  - `external.HttpPaymentGatewayAdapter`（实现 `PaymentGatewayPort`）

这样拆的好处是：

- 用例编排在应用层，领域规则在领域层，技术实现留在基础设施层
- 将来订单/支付拆成两个微服务时，变化主要集中在 `port.out` 的实现（本地调用 → 远程调用）

---

## 11. 不是“教条 DDD”，而是更实用的落地建议

- 优先做到“模块边界清晰”，再谈聚合根、领域事件这些进阶概念
- 复杂流程（跨订单/支付）优先放 `process`（应用层编排），不要把两个子域的逻辑硬塞进一个领域对象
- 所有可能变化的外部依赖（DB/Redis/第三方/MQ）都通过 `port.out` 隔离
- 当业务还很简单时：
  - 允许 domain 很薄、process 很少
  - 允许先用最小必要结构跑起来，复杂了再演进
