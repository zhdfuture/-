import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface  ISubject1{
    public void eat(String name,int num);
}
class RealSubject1 implements ISubject1{

    @Override
    public void eat(String name, int num) {
        System.out.println("我要吃 "+num + "分量的 "+name) ;
    }
}
class ProxySubject1 implements InvocationHandler{
  private  Object target;
    public Object bind(Object target) {
        // 保存真实主题对象
        this.target = target ;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this) ;
    }
    public void preHandle() {
        System.out.println("[ProxySubject1] ⽅法处理前");
    }
    public void afterHandle(){
        System.out.println("[ProxySubject1] ⽅法处理后");
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.preHandle();
        Object result=method.invoke(this.target,args);
        this.afterHandle();;
        return result;
    }
}
public class TestDemo2 {
    public static void main(String[] args) {
        ISubject1 subject =(ISubject1) new ProxySubject1().bind(new RealSubject1()) ;
        subject.eat("苹果",1) ;
    }
}

