public class PercolationStats {
   private double[]	X_t;
   private double vMean,vStdDev;
   private int times;
   
   public PercolationStats(int N, int T){
	   // perform T independent experiments on an N-by-N grid
	   if (N < 1 || T < 1) throw new IllegalArgumentException("Illeagal Argument");   
	   X_t = new double[T];
	   times = T;
	   for(int i = 0;i<T;i++){
		   Percolation p = new Percolation(N);
		   boolean percolates = false;
		   int counts = 0;
		   while(!percolates){
			   int x_pos = StdRandom.uniform(N) + 1;
			   int y_pos = StdRandom.uniform(N) + 1;
			   if(!p.isOpen(x_pos,y_pos)){
				   p.open(x_pos, y_pos);
				   counts++;
				   percolates = p.percolates();
			   }			   
		   }
		   
		   X_t[i] = (double) counts / (double) (N * N);
		   
	   }
	   
	   vMean = StdStats.mean(X_t);
	   vStdDev = StdStats.stddev(X_t);
	   
	   
   }
   public double mean(){
	   // sample mean of percolation threshold
	   return vMean;
   }
   public double stddev(){
	   // sample standard deviation of percolation threshold
	   return vStdDev;
   }
   
   public double confidenceLo() {
       double mu = mean();
       double sigma = stddev();
       return mu - 1.96*sigma / Math.sqrt(times);
   }
   
   public double confidenceHi() {
       double mu = mean();
       double sigma = stddev();
       return mu + 1.96*sigma / Math.sqrt(times);
   }

   public static void main(String[] args){
	   // test client (described below)
	   
	   int N = Integer.parseInt(args[0]);
       int T = Integer.parseInt(args[1]);
       PercolationStats percStats = new PercolationStats(N, T);
       StdOut.printf("mean = %f\n", percStats.mean());
       StdOut.printf("stddev = %f\n", percStats.stddev());
       StdOut.printf("95%% confidence interval = %f, %f\n", 
                     percStats.confidenceLo(), percStats.confidenceHi());
       
   }
}