import java.util.concurrent.Semaphore;
public class ConsumerProducer{
	Semaphore mutex,isFull,isEmpty;
	final Object[] buff;
	int inbuf,outbuf;
	public ConsumerProducer(int size){
		mutex=new Semaphore(1);
		isFull=new Semaphore(size);
		isEmpty=new Semaphore(size);
		inbuf=0;
		outbuf=0;
		buff=new Object[size];
	}

	public void deposit(Object item) throws InterruptedException{
		isFull.acquire();
		mutex.acquire();
		buff[inbuf]=item;
		inbuf=(inbuf+1)%buff.length;
		mutex.release();
		isEmpty.release();
	}

	public Object fetch()throws InterruptedException{
		isEmpty.acquire();
		mutex.acquire();
		Object ret=buff[outbuf];
		outbuf=(outbuf+1)%buff.length;
		mutex.release();
		isFull.release();
		return ret;
	}
}
