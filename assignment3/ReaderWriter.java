import java.util.concurrent.Semaphore;
public class ReaderWriter{
	Semaphore mutex,writeLock;
	int numReaders;
	public ReaderWriter(){
		mutex=new Semaphore(1);
		writeLock=new Semaphore(1);
		numReaders=0;
	}

	public void startRead()throws InterruptedException{
		mutex.acquire();
		numReaders++;
		if(numReaders==1) writeLock.acquire();
		mutex.release();
	}

	public void endRead() throws InterruptedException{
		mutex.acquire();
		numReaders--;
		if(numReaders==0) writeLock.release();
		mutex.release();
	}

	public void startWrite() throws InterruptedException{
		writeLock.acquire();
	}

	public void endWrite() throws InterruptedException{
		writeLock.release();
	}
}
