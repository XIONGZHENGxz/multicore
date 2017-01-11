public class PIncrement{
	public static int parallelIncrement(int c,int numThreads){
		long start=System.currentTimeMillis();
	}
}

class Inc implements callable{
	long e;
	int num;
	int target;
	public Inc(int c,int n){
		num=n;
		target=c;
	}
	public long call(){
		for(int i=0;i<n;i++){
			Inc t=new Inc(target,n)
	
