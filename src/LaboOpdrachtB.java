import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class LaboOpdrachtB {

    public static void main(String[] args) {
        int[] arriveTimes = {12, 31, 63, 95, 99, 154, 198, 221, 304, 346, 411, 455, 537};
        int[] serviceTimes = {40, 32, 55, 48, 18, 50, 47, 18, 28, 54, 40, 72, 12};

        Queue<Customer> customerQueue = new PriorityQueue<>();
        List<Customer> customerList = new ArrayList<>();

        //Making the customers
        for(int i = 0; i < arriveTimes.length; i++) {
            customerList.add(new Customer(arriveTimes[i], serviceTimes[i]));
        }

        //In case the input is not in order
        while(!customerList.isEmpty()){
            Customer tempCustomer = customerList.get(0);
            for(Customer customer : customerList){
                if(tempCustomer.getArriveTime() > customer.getArriveTime()){
                    tempCustomer = customer;
                }
            }

            customerQueue.add(tempCustomer);
            customerList.remove(tempCustomer);
        }

        Server firstServer = new Server();
        Server secondServer = new Server();

        List<Customer> waitingQueue =  new ArrayList<>();

        //exercise b
        for (int i = 0; i < arriveTimes[arriveTimes.length-1]; i++) {
            if(!customerQueue.isEmpty() && i == customerQueue.peek().getArriveTime()){
                waitingQueue.add(customerQueue.remove());
            }

            if(!waitingQueue.isEmpty() && firstServer.serviceAvailable(waitingQueue.get(0),i)){
                System.out.println("First Server:");
                firstServer.nextCustomer(waitingQueue.remove(0));
            }

            if(!waitingQueue.isEmpty() && secondServer.serviceAvailable(waitingQueue.get(0),i)){
                System.out.println("Second Server:");
                secondServer.nextCustomer(waitingQueue.remove(0));
            }
        }

/*
        //exercise c
        for (int i = 0; i < arriveTimes[arriveTimes.length-1]; i++) {
            if(!customerQueue.isEmpty() && i == customerQueue.peek().getArriveTime()){
                waitingQueue.add(customerQueue.remove());
            }

            if(!waitingQueue.isEmpty() && firstServer.serviceAvailable(waitingQueue.get(waitingQueue.size()-1),i)){
                System.out.println("First Server:");
                firstServer.nextCustomer(waitingQueue.remove(waitingQueue.size()-1));
            }
            if(!waitingQueue.isEmpty() && secondServer.serviceAvailable(waitingQueue.get(waitingQueue.size()-1),i)){
                System.out.println("Second Server:");
                secondServer.nextCustomer(waitingQueue.remove(waitingQueue.size()-1));
            }
        }
*/
        System.out.println("--------------------------System finished--------------------------");
    }
}

class Server{
    private int availableServiceTime = 0;

    public Server(){}

    public void nextCustomer(Customer customer){
        if(customer.getArriveTime() > availableServiceTime){
            availableServiceTime = customer.getArriveTime();
        }
        System.out.println("Customer started being served at: "+availableServiceTime);
        System.out.println("Customer arriving time: "+customer.getArriveTime());
        availableServiceTime += customer.getServiceTime();
        System.out.println("Customer done being served at: "+ availableServiceTime + "\n");
    }

    public boolean serviceAvailable(Customer customer,int i){
        if(i >= customer.getArriveTime() &&  i > availableServiceTime) return true;
        else return false;
    }
}

class Customer implements Comparable<Customer>{
    private int arriveTime;
    private int serviceTime;

    public Customer(int arrive, int service){
        arriveTime = arrive;
        serviceTime = service;
    }

    public int getArriveTime() {
        return arriveTime;
    }

    public int getServiceTime(){
        return serviceTime;
    }

    @Override
    public int compareTo(Customer o) {
        return arriveTime - o.getArriveTime();
    }
}
