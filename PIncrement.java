import java.lang.Thread;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Runnable;
public class PIncrement{
	public static int parallelIncrement(int c,int numThreads){
		return peterMethod(c,numThreads);
	}

	public static int peterMethod(int c,int numThreads){
        long start=System.currentTimeMillis();
        int m = 1200000;
    	final PetersonN p=new PetersonN(m/numThreads);
       	final Integer target=new Integer(c);
        for(int i=0;i<numThreads;i++){
			Inc in=new Inc(target,p,m/numThreads,i,0);
			Thread t=new Thread(in);
            t.start();
        }
        long end=System.currentTimeMillis();
        return (int)(end-start);
    }

	
	public static int atomicMethod(AtomicInteger c,int numThreads){
        long start=System.currentTimeMillis();
        int m = 1200000;
        for(int i=0;i<numThreads;i++){
            Thread t=new Thread(){
				public void run(){
					for(int j=0;j<m/numThreads;j++){
						while(true){
							int curr=c.get();
							int next=curr+1;
							if(c.compareAndSet(curr,next)) break;
						}
					}
				}
			};
            t.start();
        }
        long end=System.currentTimeMillis();
        return (int)(end-start);
	}


	public static int syncMethod(int c,int numThreads){
		long start=System.currentTimeMillis();
        int m = 1200000;
        Integer target=new Integer(c);
        for(int i=0;i<numThreads;i++){
            Inc in=new Inc(target,m/numThreads);
            Thread t=new Thread(in);
            t.start();
        }
        long end=System.currentTimeMillis();
        return (int)(end-start);
	}


class PetersonN {
	int n;
	int[] level;
	int[] last;
	public PetersonN(int n){
		this.n=n;
		level=new int[n];
		last=new int[n];
	}
	public void acquireCS(int id){
		for(int i=1;i<n;i++){
			level[id]=i;
			last[i]=id;
			for(int j=0;j<n;j++){
				while(j!=i && level[j]>=i && last[i]==id);
			}
		}
	}

	public void releaseCS(int id){
		level[id]=0;
	}
}
		
class Inc implements Runnable{
	Integer num;
	int count;
	int flag;
	ReentrantLock lock;
	int threadId;
	PetersonN p;
	public Inc(Integer n,PetersonN p,int count,int f,int id){
		num=n;
		lock=new ReentrantLock();
		flag=f;
		this.p=p;
		threadId=id;
	}

	public synchronized void syncIncre(){
		num++;
	}
	
	public void peterIncre(){
		p.acquireCS(threadId);
		num++;
		p.releaseCS(threadId);
	}

	public void lockIncre(){
		lock.lock();
		num++;
		lock.unlock();
	}

	public void run(){
		if(flag==0){
			while(count-->0) peterIncre();
		} else if(flag==2){
			while(count-->0) lockIncre();
		} else{
			while(count-->0) syncIncre();
		}
	}
}
}	
