import java.util.concurrent.*;
public class LExclusion implements Lock{

	int[] level;
	int[] last;
	int l;
	int n;
	int[] count;
	public LExclusion(int n,int l){
		level=new int[n-l];
		this.l=l;
		this.n=n;
		count=new int[n-l];
		for(int i=1;i<n-l;i++) count[i]=n;
		last=new int[n-l];
	}

	public void requestCS(int i){
		for(int k=1;k<level.length;k++){
			level[i]=k;
			last[k]=i;
			count[k]--;
			for(int j=0;j<n;j++){
				while(count[k-1]<l && last[k]==i);
			}
		}
	}

	public void leaseCS(int i){
		level[i]=0;
		for(int j=1;j<n-l;j++) count[j]++;
	}

}

