import java.lang.InterruptedException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
public class LockBasedQueue{
	final int size;
	final Object[] buff;
	int inBuf,outBuf,count;
	final ReentrantLock monitor;
	final Condition notEmpty,notFull;
	public LockBasedQueue(int size){
		this.size=size;
		buff=new Object[size];
		monitor=new ReentrantLock();
		notEmpty=monitor.newCondition();
		notFull=monitor.newCondition();
		inBuf=0;
		outBuf=0;
		count=0;
	}


	public void add(Object obe) throws InterruptedException{
		monitor.lock();
		try{
			while(count==buff.length)
				notFull.await();
			buff[inBuf]=obe;
			inBuf=(inBuf+1)%size;
			count++;
			notEmpty.signal();
		} finally{
			monitor.unlock();
		}
	}

	public Object poll() throws InterruptedException{
		monitor.lock();
		try{
			while(count==0)
				notEmpty.await();
			Object ret=buff[outBuf];
			outBuf=(outBuf-1)%size;
			count--;
			notFull.signal();
			return ret;
		} finally {
			monitor.unlock();
		}
	}
}
