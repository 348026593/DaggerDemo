# DaggerDemo
Dagger2&amp;MVP Demo
### Dagger2简介
Dagger2是Dagger的升级版，是一个依赖注入框架，依赖注入是面向对象编程的一种设计模式，其目的是为了降低程序耦合。

核心是通过java注解的方式进行依赖注入。

### Dagger2用法
* 配置apt插件（在build.gradle中添加代码）
```
 dependencies {
        classpath 'com.android.tools.build:gradle:2.3.3'
        //添加apt插件
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
        classpath 'me.tatarka:gradle-retrolambda:3.2.5'
        ...
    }
```
* 添加依赖（在build.gradle(Module)中添加代码）
```
  ...
  apply plugin: 'com.growingio.android'
  apply from: "../../SaleHouse/node_modules/react-native-code-push/android/codepush.gradle"
  //添加如下代码，应用apt插件
  apply plugin: 'com.neenbedankt.android-apt'
  ...
  dependencies {
      ...
      compile 'com.google.dagger:dagger:2.4'
      apt 'com.google.dagger:dagger-compiler:2.4'
      //java注解
      compile 'org.glassfish:javax.annotation:10.0-b28'
      ...
  }
```
### 使用Dagger2
举例：Activity持有presenter的引用，并在Activity中实例化这个presenter，即Activity依赖presenter，presenter又需要依赖View接口与Model接口，从而更新UI。
![结构图](http://upload-images.jianshu.io/upload_images/7752337-96a025c133293c91.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

相比MVP分包，加入Dagger后，需要多加入Module类和Component接口以实现依赖关系。

##### 使用步骤：
1. Activity要依赖presenter，先在activity中注入依赖presenter。
```
public class MainActivity extends AppCompatActivity implements MainContract.View{

    @Inject
    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainComponent.builder()
                .mainModule(new MainModule(this, new MainModel()))
                .build()
                .inject(this);
        mMainPresenter.loadData();
    }

    @Override
    public void updateUI(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
```
Activity中声明MainPresenter时试用@Inject注解（需要注意注解变量不能私有）
onCreate中的DaggerMainComponent是在项目rebuild后才能生成的类，presenter的初始化工作需要注释或者rebuild项目后再写
2. MainPresenter中构造方法也需要试用注解
```
public class MainPresenter extends MainContract.Presenter {
    @Inject
    MainPresenter(MainContract.View view, MainContract.Model model) {
        super(view, model);
    }

    @Override
    public void loadData() {
        mView.updateUI(mModel.getData());
    }

}
```
我们Presenter中需要在构造时传入View与Model，Presenter依赖View&Model.
3. 新建MainModule类，Module类是用来提供依赖的，MainModlue是一个注解类，用@Module注解标注，需要说明的是Module类主要是为了提供那些没有构造函数的类的依赖，这些类无法用@Inject标注，比如第三方类库，系统类，以及上面示例的View接口。
```
/**
 * Module
 * Created by lihq on 2017/9/4.
 */
@Module
public class MainModule {
    
    private final MainContract.View mView;
    private final MainContract.Model mModel;

    public MainModule(MainContract.View view, MainContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Provides
    MainContract.View provideMainView() {
        return mView;
    }

    @Provides
    MainContract.Model provideMainModel() {
        return mModel;
    }
}
```
其中@Module用来标注这个注解类，@Provides标注以provide开头的方法，将presenter需要的view&model返回，用来给presneter提供依赖
4. 创建MainConponent接口，这个接口用@Component标注，括号里的就是刚刚创建的MainMoudule注解类，这个接口中还有一个inject方法需要传入MainActivity。
```
/**
 * Component
 * Created by lihq on 2017/9/4.
 */

@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
```
联系之前Activity中初始化presenter的代码来看，Component接口就是用来建立桥梁的。
通过new MainModule(this)将view传递到MainModule里，然后MainModule里的provideMainView()方法返回这个View，当去实例化MainPresenter时，发现构造函数有个参数，此时会在Module里查找提供这个依赖的方法，将该View传递进去，这样就完成了presenter里View的注入。
5. 做完这些，rebuild项目，会看到在build文件夹中生成了这些类（factory，component等）


![rebuild](http://upload-images.jianshu.io/upload_images/7752337-3df2e8e6dffdc344.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这些就是通过apt插件在编译阶段生成相应的注入代码

6. 最后一步，放开activity中初始化presenter的代码，获取presenter实例。

### 总结
* dagger2解决了mvp结构互相依赖的耦合问题
* 使用孰能生巧吧，主要还是需要理解他的注入过程，刚开始用感觉步骤还挺麻烦的。
* 需注意需要rebuild这个过程，使apt插件生成dagger代码。
