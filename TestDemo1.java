import java.lang.reflect.Constructor;

//核心操作的接口
interface ISubject{
    public void eat();  //核心业务
}
class RealSubject implements ISubject{

    @Override
    public void eat() {
        System.out.println("早起吃水果");
    }
}
class ProxySubject implements ISubject{
private ISubject subject;
public ProxySubject(ISubject subject){
this.subject=subject;
}
public void prepare(){
    System.out.println("准备水果");
}
public void aftereat(){
    System.out.println("收拾");
}
    @Override
    public void eat() {
    this.prepare();
    this.subject.eat();  //真实业务
        this.aftereat();

    }
}
//工厂类
 class Factory {
    private Factory() {
    }

    public static <T> T getInstance(String className) {
        T t = null;
        try {
            t = (T) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return t;
    }

    //
//    public static <T> T getInstance(String className, Object obj) {
//        T t = null;
//        try {
//            Constructor<?> cs = Class.forName(className).getConstructor(obj.getClass().getInterfaces()[0]);
//            t = (T) cs.newInstance(obj) ;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return t;
//    }
    public static <T> T getInstance(String proxyClassName, String realClassname) {
        T t = null;
        T realObj = getInstance(realClassname);
        try {
            Constructor<?> cs =
                    Class.forName(proxyClassName).getConstructor(realObj.getClass().getInterfaces()[0]);
            t = (T) cs.newInstance(realObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}

    public class TestDemo1 {
        public static void main(String[] args) {
//            ISubject subject =Factory.getInstance("ProxySubject",Factory.getInstance("RealSubject"));
            ISubject subject =Factory.getInstance("ProxySubject","RealSubject");
            subject.eat();
        }
    }
