package cn.archangel.sort;

/**
 * 希尔排序
 * @author Esc_penger
 *
 */
public class ShellSort {

	public static void main(String[] args) {
		Integer a[] = new Integer[]{4,3,5,8,9,2,7,1};
//		shellSort(a, 8);
		for (Integer temp : a) {
			System.out.print(temp);
		}
		System.out.println();
		quickSort(a, 0, a.length-1);
		for (Integer temp : a) {
			System.out.print(temp);
		}
		System.out.println();
	}
	
	
	/**
	 * 快速排序
	 * @param a
	 * @param start
	 * @param end
	 */
	private static void quickSort(Integer[] a,int start,int end) 
	   { 
		System.out.println("start is:"+start+"  end is :"+end);
	       if(start<end) 
	       {
	           int key=a[start];//初始化保存基元  
	           int i=start,j;//初始化i,j  
	           for(j=start+1;j<=end;j++) {
	             System.out.println("j="+j+"   a[j]="+a[j]);
	               if(a[j]<key)//如果此处元素小于基元，则把此元素和i+1处元素交换，并将i加1，如大于或等于基元则继续循环  
	               {
	            	   
	            	   System.out.println("调换数据"+(i+1)+" 和"+j+"值为:"+a[i+1]+"和"+a[j]);
	            	   int temp=a[j]; 
	                   a[j]=a[i+1]; 
	                   a[i+1]=temp; 
	                   i++; 
	                   for (Integer item : a) {
	   	   					System.out.print(item);
		   	   			}
		   	           System.out.println("-----");
		               } 
	           }
	           
	           a[start]=a[i];//交换i处元素和基元  
	           a[i]=key;
	           for (Integer temp : a) {
	   				System.out.print(temp);
	   			}
	           System.out.println();
	           quickSort(a, start, i-1);//递归调用 
	           quickSort(a, i+1, end);
	       }
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
			System.out.println("i  "+i+"   i-dk"+i+"-"+dk+"  ="+(i-dk));
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
