package q2;

// TODO
// Implement the bakery algorithm

public class BakeryLock implements MyLock {
    boolean[] choosen;
	int[] nums;
	public BakeryLock(int numThread) {
   		nums=new int[numThread];
		choosen=new boolean[numThread];
   	}

    @Override
    public void lock(int myId) {
		//choose a number
		choosen[myId]=true;
		for(int i=0;i<nums.length;i++){
			if(nums[myId]>nums[i]) 
				nums[myId]=nums[i];
		nums[myId]++;
		choosen[myId]=false;
		//check critical section condition
		for(int j=0;j<nums.length;j++){
			while(choosen[j]);
			while(nums[j]!=0 && nums[j]<nums[myId] || (nums[j]==nums[i] && j<i));
		}
		}
    }

    @Override
    public void unlock(int myId) {
   		nums[myId]=0;
   }
}
