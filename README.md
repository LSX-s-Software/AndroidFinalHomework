# AndroidFinalHomework

> 安卓大作业——书本商店

## 软件功能

1. 包含一个登录界面，可供用户登录和注册，登录成功后下次登录时无需再输入用户名;
2. 主界面有三个 tab，分别是图书列表、购物车和历史订单;
3. 图书列表包括以下功能:
   - 可以浏览图书，每本图书有封面图标、书名、价格;
   - 图书按类别分区，区内展示本类别图书信息;
   - 点击某书后，可以查看该书详细信息，包括该书封面大图、单价、书名、 作者、书号和详情介绍，详情页可以将该书加入购物车，加入购物车后会在购物车页 tab 上显示数量;
4. 购物车页包括以下功能:
   - 所有选购图书的列表;
   - 可以对该列表中的图书进行删除;
   - 有一个“支付”按钮，确认后显示一个支付成功页面，并将购物车的内容加入历史订单中;
5. 历史订单页包括以下功能:
   - 列出订单编号、图书数目、总价、购买时间;
   - 点击后展示该订单图书明细;
6. 所有的数据，包括用户、图书、购物车和历史订单信息，全部存储在 SQLite 数据库中。

## 目录结构

```
├── app
│   ├── build.gradle
│   ├── libs
│   ├── proguard-rules.pro
│   └── src
│       ├── main
│       │   ├── AndroidManifest.xml
│       │   ├── java
│       │   │   └── com
│       │   │       └── lsx
│       │   │           └── finalhomework
│       │   │               ├── MyAuth.java                         // 用户管理类
│       │   │               ├── MyDBHelper.java                     // 数据库访问类
│       │   │               ├── NWImageView.java                    // 支持网络图片的ImageView
│       │   │               ├── adapters
│       │   │               │   ├── MyBookRecyclerViewAdapter.java  // 图书列表Adapter
│       │   │               │   ├── MyCartRecyclerViewAdapter.java  // 购物车Adapter
│       │   │               │   └── MyOrderRecyclerViewAdapter.java // 订单列表Adapter
│       │   │               ├── controllers
│       │   │               │   ├── BookDetailFragment.java         // 图书详情
│       │   │               │   ├── BookFragment.java               // 图书列表
│       │   │               │   ├── CartFragment.java               // 购物车
│       │   │               │   ├── LoginActivity.java              // 注册登录页面
│       │   │               │   ├── MainActivity.java               // 主页
│       │   │               │   ├── OrderDetailFragment.java        // 订单详情
│       │   │               │   └── OrderFragment.java              // 订单列表
│       │   │               └── entities                            // 实体类
│       │   │                   ├── Book.java                       // 图书
│       │   │                   ├── BookService.java                // 图书操作
│       │   │                   ├── Cart.java                       // 购物车
│       │   │                   ├── CartItem.java                   // 购物车项
│       │   │                   ├── Order.java                      // 订单
│       │   │                   ├── OrderDetail.java                // 订单详情
│       │   │                   └── OrderService.java               // 订单操作
│       │   └── res                                                 // 资源文件
│       │       ├── color
│       │       ├── drawable
│       │       ├── drawable-v24
│       │       ├── entity
│       │       ├── layout                                          // 布局文件
│       │       │   ├── activity_login.xml                          // 注册登录页面
│       │       │   ├── activity_main.xml                           // 主页面
│       │       │   ├── book_fragment_item.xml                      // 图书列表项
│       │       │   ├── book_fragment_item_list.xml                 // 图书列表页面
│       │       │   ├── book_fragment_item_wide.xml                 // 图书列表项（宽）
│       │       │   ├── cart_fragment_item.xml                      // 购物车列表项
│       │       │   ├── cart_fragment_item_list.xml                 // 购物车列表页面
│       │       │   ├── fragment_book_detail.xml                    // 图书详情页面
│       │       │   ├── fragment_order_detail.xml                   // 订单详情页面
│       │       │   ├── list_header.xml                             // 分组标题
│       │       │   ├── order_fragment_item.xml                     // 订单列表项
│       │       │   ├── order_fragment_item_list.xml                // 订单列表页面
│       │       │   └── pager_item.xml                              // 分组标签
│       │       ├── menu
│       │           └── bottom_nav_menu.xml                         // 底部导航菜单项
│       │       ├── mipmap-anydpi-v26
│       │       ├── mipmap-hdpi
│       │       ├── mipmap-mdpi
│       │       ├── mipmap-xhdpi
│       │       ├── mipmap-xxhdpi
│       │       ├── mipmap-xxxhdpi
│       │       ├── navigation
│       │       │   └── mobile_navigation.xml                       // 导航关系
│       │       ├── values
│       │       └── values-night
├── build.gradle
├── gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── local.properties
├── LICENSE
└── settings.gradle
```

