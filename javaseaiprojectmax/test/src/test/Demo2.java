package test;

public class Demo2 {
    public static void main(String[] args) {
        String a = "10";
        String b = new String("10");
        String c = "10";
        String d = "1";
        String e = "0";
        String f = d + e;
        String g = "1" + "0";
        System.out.println(a == c);
        System.out.println(b == a);
        System.out.println(a == f);
        System.out.println(b == f);
        System.out.println(b == g);
        System.out.println(f == g);
        System.out.println(a == g);
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(c.hashCode());
        System.out.println(d.hashCode());
        System.out.println(e.hashCode());
        System.out.println(f.hashCode());
        System.out.println(g.hashCode());
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));
        System.out.println(System.identityHashCode(c));
        System.out.println(System.identityHashCode(d));
        System.out.println(System.identityHashCode(e));
        System.out.println(System.identityHashCode(f));
        System.out.println(System.identityHashCode(g));
    }
}

interface A{
    abstract void Shape();
}