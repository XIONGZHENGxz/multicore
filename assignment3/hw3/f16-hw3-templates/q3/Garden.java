package q3;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
public class Garden {
// you are free to add members
	ReentrantLock shovel;
	int numOfHoles,numOfSeedHoles,numOfFilledHoles,MAX;
	Condition emptyHole,filledHole,maxWait;
	public Garden(){
		// implement your constructor here
		shovel=new ReentrantLock();
		emptyHole=shovel.newCondition();
		filledHole=shovel.newCondition();
		maxWait=shovel.newCondition();
		numOfHoles=0;
		numOfSeedHoles=0;
		numOfFilledHoles=0;
		MAX=10;
	}
	public void startDigging() throws InterruptedException{
        // implement your startDigging method here
		shovel.lock();
		if(numOfHoles-numOfFilledHoles>=MAX){
			shovel.unlock();
			maxWait.await();
		}
		emptyHole.signal();
	}
	public void doneDigging(){
        // implement your doneDigging method here
		numOfHoles++;
		shovel.unlock();
	} 
	public void startSeeding() throws InterruptedException{
        // implement your startSeeding method here
		if(numOfHoles==numOfSeedHoles)
			emptyHole.await();
		filledHole.signal();
	}
	public void doneSeeding(){
        // implement your doneSeeding method here
		numOfSeedHoles++;
	} 
	public void startFilling() throws InterruptedException{
        // implement your startFilling method here
		shovel.lock();
		if(numOfSeedHoles==numOfFilledHoles){
			shovel.unlock();
			filledHole.await();
		}
		maxWait.signal();
	}
	public void doneFilling(){
        // implement your doneFilling method here
		shovel.unlock();
		numOfFilledHoles++;
	}

// You are free to implements your own Newton, Benjamin and Mary
// classes. They will NOT count to your grade.
	protected static class Newton implements Runnable {
		Garden garden;
		public Newton(Garden garden){
			this.garden = garden;
		}
		@Override
		public void run(){
		    while (true) {
                garden.startDigging();
			    dig();
				garden.doneDigging();
			}
		} 
		
		private void dig(){
		}
	}
	
	protected static class Benjamin implements Runnable {
		Garden garden;
		public Benjamin(Garden garden){
			this.garden = garden;
		}
		@Override
		public void run(){
		    while (true) {
                garden.startSeeding();
				plantSeed();
				garden.doneSeeding();
			}
		} 
		
		private void plantSeed(){
		}
	}
	
	protected static class Mary implements Runnable {
		Garden garden;
		public Mary(Garden garden){
            this.garden = garden;
		}
		@Override
		public void run(){
		    while (true) {
                garden.startFilling();
			 	Fill();
			 	garden.doneFilling();
			}
		} 
		
		private void Fill(){
		}
	}

}
