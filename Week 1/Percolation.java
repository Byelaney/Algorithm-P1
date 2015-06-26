public class Percolation {
	private boolean[] openSite;
	private int length;
	private WeightedQuickUnionUF wquUF;
	private WeightedQuickUnionUF wquTOPUF;
	private boolean percolates;
	
	public Percolation(int N){
		if(N < 1) throw new IllegalArgumentException();
		
		openSite = new boolean[N*N + 1];
		length = N;
		wquUF = new WeightedQuickUnionUF(N*N + 2);
		wquTOPUF = new WeightedQuickUnionUF(N*N + 1);
		percolates = false;
		
		for(int i =0;i<openSite.length;i++)
			openSite[i] = false;
	}
	
	private void boundsCheck(int i ,int j){
		if(i < 1 || i > length) throw new IndexOutOfBoundsException();
		if(j < 1 || j > length) throw new IndexOutOfBoundsException();	
	}
	
	public void open(int i ,int j){
		boundsCheck(i,j);
		if(isOpen(i,j))
			return;
		
		int siteIndex = (i-1) * length + j;
		openSite[siteIndex] = true;
		
		// first row
		if(i == 1){
			wquTOPUF.union(siteIndex, 0);
			wquUF.union(siteIndex, 0);
		}
		//last row
		if(i == length){
			wquUF.union(siteIndex, length*length + 1);
		}
		
		int coordinatex,coordinatey;
		//connect left
		coordinatex = i;
		coordinatey = j - 1;
		if(coordinatey > 0){
			boolean open = isOpen(coordinatex,coordinatey);
			if(open){
				wquTOPUF.union(siteIndex, siteIndex-1);
				wquUF.union(siteIndex, siteIndex-1);
			}			
		}
		
		//connect right
		coordinatex = i;
		coordinatey = j + 1;
		if(coordinatey <= length){
			boolean open = isOpen(coordinatex,coordinatey);
			if(open){
				wquTOPUF.union(siteIndex, siteIndex+1);
				wquUF.union(siteIndex, siteIndex+1);
			}			
		}	
		
		//connect upper
		coordinatex = i - 1;
		coordinatey = j;
		if(coordinatex > 0){
			boolean open = isOpen(coordinatex,coordinatey);
			if(open){
				wquTOPUF.union(siteIndex, siteIndex - length);
				wquUF.union(siteIndex, siteIndex - length);
			}	
		}
		
		//connect lower
		coordinatex = i + 1;
		coordinatey = j;
		if(coordinatex <= length){
			boolean open = isOpen(coordinatex,coordinatey);
			if(open){
				wquTOPUF.union(siteIndex, siteIndex + length);
				wquUF.union(siteIndex, siteIndex + length);
			}	
		}
	}
	
	public boolean isOpen(int i ,int j){
		boundsCheck(i,j);
		int siteIndex = (i-1) * length + j;
		return openSite[siteIndex];		
	}
	
	public boolean isFull(int i ,int j){
		if(!isOpen(i,j))
			return false;
		
		boundsCheck(i,j);
		int siteIndex = (i-1) * length + j;		
		return wquTOPUF.connected(siteIndex, 0);
	}
	
	public boolean percolates(){
		if(percolates)
			return true;
		
		if(wquUF.connected(0,length*length + 1)){
			percolates = true;
			return true;
		}
		
		return false;
	}
	
	public static void main(String []args){
		Percolation p = new Percolation(4);
		System.out.println(p.isFull(1, 1));
	}
	
}