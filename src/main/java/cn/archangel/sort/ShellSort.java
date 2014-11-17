package cn.archangel.sort;

/**
 * 希尔排序
 * @author Esc_penger
 *
 */
public class ShellSort {

	public static void main(String[] args) {
		Integer a[] = new Integer[]{3,1,5,7,2,4,9,6};
		shellSort(a, 8);
	}
	
	
	private static void shellSort(Integer[] a,int n){
		int dk=n/2;
		while(dk>=1){
			shellInsertSort(a,n,dk);
			dk=dk/2;
		}
	}


	private static void shellInsertSort(Integer[] a, int n, int dk) {
		for(int i=dk;i<n;++i){
			System.out.println("i"+i+"i-dk"+i+"-"+dk+"="+(i-dk));
			if(a[i]<a[i-dk]){
				int j=i-dk;
				System.out.println("j"+j);
				int x=a[i];
				System.out.println("a["+i+"]"+"="+a[i]);
				a[i]=a[i-dk];
				while(x<a[j]){
					a[j+dk]=a[j];
					j-=dk;
				}
				a[j+dk]=x;
			}
			print(a,n,i);
		}
		
	}


	private static void print(Integer[] a, int n, int i) {
		System.out.println(i+" : ");
		for(int j=0;j<8;j++){
			System.out.print(a[j]);
		}
		System.out.println();
	}

}
