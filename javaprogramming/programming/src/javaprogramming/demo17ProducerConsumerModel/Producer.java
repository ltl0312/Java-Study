package javaprogramming.demo17ProducerConsumerModel;

public class Producer extends Thread{
    private Resource resource;
    public Producer() {
    }
    public Producer(Resource resource) {
        this.resource = resource;
    }
    @Override
    public void run() {
        while (true) {
            synchronized (resource) {
                if (resource.getCount() >= 10) {
                    try {
                        resource.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    resource.notifyAll();
                }
                resource.setName("产品" + resource.getCount());
                resource.setCount(resource.getCount() + 1);
                System.out.println(getName() + "生产了" + resource.getName());
                System.out.println("库存：" + resource.getCount());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
