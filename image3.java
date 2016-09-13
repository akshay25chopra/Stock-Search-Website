
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;




public class image3 {
   

    	JFrame frame;
    	JLabel lbIm1;
    	JLabel lbIm2;
    	public void showIms(String[] args){    	
        int n1= Integer.parseInt(args[1]);

        int width = 512;
        int height = 512;
        int m1=Math.round(n1/4096);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage img3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
     
        try {
        	File file = new File(args[0]);
        	InputStream is = new FileInputStream(file);
        	
        	long len = file.length();
        	byte[] bytes = new byte[(int)len];
	    
        	int offset = 0;
        	int numRead = 0;
        	while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        	}
    
    	int ind = 0;
		for(int y = 0; y < height; y++){
	
			for(int x = 0; x < width; x++){
		 
				byte a = 0;
				byte r = bytes[ind];
				byte g = bytes[ind+height*width];
				byte b = bytes[ind+height*width*2]; 
				
				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
				
				img.setRGB(x,y,pix);
				ind++;
			}
		}

		byte red1[]=new byte[(int)len];
		byte green1[]=new byte[(int)len];
		byte blue1[]=new byte[(int)len];


		int r[][]=new int[width][height];
		int g[][]=new int[width][height];
		int b[][]=new int[width][height];
		int rmod[][]=new int[width][height];
		int gmod[][]=new int[width][height];
		int bmod[][]=new int[width][height];


		int argb;
		int incr=0;
		for(int y = 0; y < height; y++)
		{
	
			for(int x = 0; x < width; x++)
			{


				argb = img.getRGB(x, y);

				r[y][x] = (argb >> 16) & 0x000000FF;
				g[y][x]=  (argb >> 8) & 0x000000FF;
				b[y][x]=  (argb) & 0x000000FF;  

			}
		}

		for(int y = 0; y < height; y++)
		{
	
			for(int x = 0; x < width; x++)
			{

					red1[incr]=(byte)r[y][x];
					green1[incr]=(byte)g[y][x];
					blue1[incr]=(byte)b[y][x];

					incr++;
			}
		}

		double r1mod[][]=new double[8][8];
		double g1mod[][]=new double[8][8];
		double b1mod[][]=new double[8][8];

		double r1[][]=new double[8][8];
		double g1[][]=new double[8][8];
		double b1[][]=new double[8][8];
		int p[][]=new int[8][8];
		int gg[][]=new int[8][8];
		int bb[][]=new int[8][8];

		int ctr=0,index=0,indexx=0;
		double redmod[]=new double[262144];
		double red1mod[]=new double[262144];
		double greenmod[]=new double[262144];
		double green1mod[]=new double[262144];
		double bluemod[]=new double[262144];
		double blue1mod[]=new double[262144];

		int in=0,inc=0;
		for(int k=0;k<64;k++)
		{
    
			for(int j=0;j<64;j++)
			{
  
				for(int m=0;m<8;m++)
				{
					in=8*(j+k*width);
					for(int n=0;n<8;n++)
					{
						p[m][n]=(red1[in+width*m]& 0x000000FF);
						gg[m][n]=(green1[in+width*m]& 0x000000FF);
						bb[m][n]=(blue1[in+width*m]& 0x000000FF);
						in++;
					}
				}
				
				
				//r1=DCT(in,p,height,width);
				//Performing DCT for the R Channel
				int ind1=in-8;
				// double m[][]=new double[8][8];
				 double sum=0;

				 for(int u=0;u<8;u++)
				 {
					 for(int v=0;v<8;v++)	
					 {	
						 r1[u][v]=0;
					 }
				 }

				 for(int u=0;u<8;u++)
				 {
					 for(int v=0;v<8;v++)
					 {
						 sum=0;
						 for(int i=0;i<8;i++)
						 {
							 for(int j1=0;j1<8;j1++)
							 {
			    
								 if(u==0)
								 {
									 if(v==0)
									 {
										 sum=sum+0.125*(p[i][j1])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j1+1)*3.14*v/16));
			        
									 }  
									 else if(v!=0)
									 {    
										 sum=sum+0.25*0.707*(p[i][j1])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j1+1)*3.14*v/16));
									 }
								 }

								 else if(u!=0)
								 {
									 if(v==0)
									 {
										 sum=sum+0.25*0.707*(p[i][j1])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j1+1)*3.14*v/16));
									 }
			    
									 else if(v!=0)
									 {    sum=sum+0.25*(p[i][j1])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j1+1)*3.14*v/16));

									 }	
			   
								 }
							 }
						 }
						 r1[u][v]=sum;
					 }
				 }
				 
				 //Performing DCT for G Channel
				//g1=DCT(in,gg,height,width);
				 
				 int ind2=in-8;
				// double m[][]=new double[8][8];
				 double sum1=0;

				 for(int u=0;u<8;u++)
				 {
					 for(int v=0;v<8;v++)	
					 {	
						 g1[u][v]=0;
					 }
				 }


				 for(int u=0;u<8;u++)
				 {
					 for(int v=0;v<8;v++)
					 {
						 sum1=0;
						 for(int i=0;i<8;i++)
						 {
							 for(int j2=0;j2<8;j2++)
							 {
			    
								 if(u==0)
								 {
									 if(v==0)
									 {
										 sum1=sum1+0.125*(gg[i][j2])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j2+1)*3.14*v/16));
			        
									 }  
									 else if(v!=0)
									 {    
										 sum1=sum1+0.25*0.707*(gg[i][j2])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j2+1)*3.14*v/16));
									 }
								 }

								 else if(u!=0)
								 {
									 if(v==0)
									 {
										 sum1=sum1+0.25*0.707*(gg[i][j2])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j2+1)*3.14*v/16));
									 }
			    
									 else if(v!=0)
									 {    sum1=sum1+0.25*(gg[i][j2])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j2+1)*3.14*v/16));

									 }	
			   
								 }
							 }
						 }
						 g1[u][v]=sum1;
					 }
				 }
				 
				 //Performing DCT for B Channel
				//b1=DCT(in,bb,height,width);
				
				 int ind3=in-8;
				// double m[][]=new double[8][8];
				 double sum2=0;

				 for(int u=0;u<8;u++)
				 {
					 for(int v=0;v<8;v++)	
					 {	
						 b1[u][v]=0;
					 }
				 }


				 for(int u=0;u<8;u++)
				 {
					 for(int v=0;v<8;v++)
					 {
						 sum2=0;
						 for(int i=0;i<8;i++)
						 {
							 for(int j3=0;j3<8;j3++)
							 {
			    
								 if(u==0)
								 {
									 if(v==0)
									 {
										 sum2=sum2+0.125*(bb[i][j3])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j3+1)*3.14*v/16));
			        
									 }  
									 else if(v!=0)
									 {    
										 sum2=sum2+0.25*0.707*(bb[i][j3])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j3+1)*3.14*v/16));
									 }
								 }

								 else if(u!=0)
								 {
									 if(v==0)
									 {
										 sum2=sum2+0.25*0.707*(bb[i][j3])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j3+1)*3.14*v/16));
									 }
			    
									 else if(v!=0)
									 {    sum2=sum2+0.25*(bb[i][j3])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*j3+1)*3.14*v/16));

									 }	
			   
								 }
							 }
						 }
						 b1[u][v]=sum2;
					 }
				 }

    //Performing zigzag for 
		{       
			if(m1==1)            
			{
				for(int x = 1; x < 8; x++)
				{
                 r1[0][x]=0;
                 g1[0][x]=0;
                 b1[0][x]=0;
                }
                for(int y = 1; y <8; y++)
                {
                        r1[y][0]=0;
                        g1[y][0]=0;
                        b1[y][0]=0;
                }                 
                   
                for(int x = 1; x < 8; x++)
                {
                	for(int y = 1; y <8; y++)
                	{
                		r1[y][x]=0;
                		g1[y][x]=0;
                		b1[y][x]=0;
                	}
                }
			}
			
			
            else if(m1==2){
                for(int x = 2; x < 8; x++){
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                   }
           for(int x = 1; x < 8; x++){
                   for(int y = 1; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                 
                   
                   }                 
                   }
              
             for(int y=1;y<8;y++)
             {
r1[y][0]=0;
g1[y][0]=0;
b1[y][0]=0;
             }

                }

            else if(m1==3)
            {
                for(int x = 2; x < 8; x++){
                   
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                   }
             for(int y=2;y<8;y++)
             {
r1[y][0]=0;
g1[y][0]=0;
b1[y][0]=0;
             }
   
           for(int x = 1; x < 8; x++){
                   for(int y = 1; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
 
                   
                   
                   }                 
                   }
           
            
            }
            
            else if(m1==4)
            {
                for(int x = 2; x < 8; x++){
                   
                   r1[0][x]=0;
                 g1[0][x]=0;
                   b1[0][x]=0;
                    
                
                }
             for(int y=3;y<8;y++)
             {
r1[y][0]=0;
g1[y][0]=0;
b1[y][0]=0;

             
             }
   
           for(int x = 1; x < 8; x++){
                   for(int y = 1; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
 
                   
                   }                 
                   }
               
            
            }
            
            else if(m1==5)
            {
                    for(int x = 2; x < 8; x++){
                   
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                  
                    }
             for(int y=3;y<8;y++)
             {
r1[y][0]=0;
g1[y][0]=0;
b1[y][0]=0;

             }
   
             double t=r1[1][1];
       
                   double t2=g1[1][1];
             double t3=b1[1][1];
       
             for(int x = 1; x < 8; x++){
                   for(int y = 1; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                  
                   
                   }                 
                   }
           r1[1][1]=t;
           g1[1][1]=t2;
           b1[1][1]=t3;

            
            }
            
            else if(m1==6)
            {
                    for(int x = 3; x < 8; x++){
                   
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;

                    
                    
                    }
             for(int y=3;y<8;y++)
             {
r1[y][0]=0;
g1[y][0]=0;
b1[y][0]=0;
             }
   
             double t=r1[1][1];
             double t2=g1[1][1];
             double t3=b1[1][1];
 
             
             for(int x = 1; x < 8; x++){
                   for(int y = 1; y <8; y++){
                    r1[y][x]=0;
                   g1[y][x]=0;
                   b1[y][x]=0;
                   
                   
                   }                 
                   }
           r1[1][1]=t;
           g1[1][1]=t2;
           b1[1][1]=t3;

            
            }
           
            else if(m1==7)
            {
                    for(int x = 4; x < 8; x++){
                   
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;

                    
             }
             for(int y=3;y<8;y++)
             {
r1[y][0]=0;
g1[y][0]=0;
b1[y][0]=0;
             }
   
             double t=r1[1][1];
             double t2=g1[1][1];
             double t3=b1[1][1];
 
             for(int x = 1; x < 8; x++){
                   for(int y = 1; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   
                   
                   }                 
                   }
           r1[1][1]=t;
           g1[1][1]=t2;
           b1[1][1]=t3;

            
            }
            
            else if(m1==8)
            {
                    for(int x = 4; x < 8; x++){
                   
                   r1[0][x]=0;
                  
                     g1[0][x]=0;
                   b1[0][x]=0;
                  
                    }
             for(int y=3;y<8;y++)
             {
            	 r1[y][0]=0;
            	 g1[y][0]=0;
            	 b1[y][0]=0;

             }
   
             double t=r1[1][1];
             double t2= r1[2][1];
             double t3=g1[1][1];
             double t4= g1[2][1];
             double t5=b1[1][1];
             double t6= b1[2][1];
 
             double t7= r1[1][2];
             double t8= g1[1][2];
             double t9= b1[1][2];

             for(int x = 1; x < 8; x++)
             {
                   for(int y = 1; y <8; y++)
                   {
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
             }
             r1[1][1]=t;
             r1[2][1]=t2; 
        
             g1[1][1]=t3;
             g1[2][1]=t4; 
             b1[1][1]=t5;
             b1[2][1]=t6; 
             r1[1][2]=t7;
             g1[1][2]=t8;
             b1[1][2]=t9;
            
            }
            
            else if(m1==9)
            {
                    for(int x = 4; x < 8; x++){
                  
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;

                    
                    }
                    for(int x = 3; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;
                   }
             for(int y=3;y<8;y++)
             {
            for(int x = 0; x <= 1; x++){
        
                 r1[y][x]=0;
        
                 g1[y][x]=0;
        
                 b1[y][x]=0;
             }
             }
             
             for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                  
                   }                 
                   }
            }
            
             else if(m1==10)
            {
                    for(int x = 4; x < 8; x++){
                  
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
         
                    
                    
                    }
                    for(int x = 3; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;
                   }
             for(int y=3;y<8;y++)
             {
        
                 r1[y][1]=0;
              g1[y][1]=0;
              b1[y][1]=0;
            
             }
             for(int y=4;y<8;y++)
             {
                  r1[y][0]=0;
               g1[y][0]=0;
               b1[y][0]=0;
            
             }
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
               
                   
                   }                 
                   }
            }
           
       else if(m1==11)
            {
                    for(int x = 4; x < 8; x++){
                  
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;

                    
                    
                    }
                    for(int x = 3; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;

                    
                    }
             for(int y=3;y<8;y++)
             {
        
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
             for(int y=5;y<8;y++)
             {
                  r1[y][0]=0;
                  g1[y][0]=0;
                  b1[y][0]=0;
            
             }
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
 
                   
                   
                   }                 
                   }
            }
            
       else if(m1==12)
            {
                    for(int x = 4; x < 8; x++){
                  
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;

                    
                    
                    
                    }
                    for(int x = 3; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;

                    
                    
                    }
             for(int y=4;y<8;y++)
             {
        
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
           
             }
             for(int y=5;y<8;y++)
             {
                  r1[y][0]=0;
                  g1[y][0]=0;
                  b1[y][0]=0;
            
             }
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
    
                   
                   }                 
                   }
            }
            
           else if(m1==13)
            {
                    for(int x = 4; x < 8; x++){
                  
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                    
                   }
                    for(int x = 3; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;
                   }
                   double t=r1[2][2];
                   double t2=g1[2][2];
                   double t3=b1[2][2];
                   
             for(int y=4;y<8;y++)
             {
        
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
             for(int y=5;y<8;y++)
             {
                  r1[y][0]=0;
                  g1[y][0]=0;
                  b1[y][0]=0;
            
             }
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   
                   
                   
                   }                 
                   }
            r1[2][2]=t;
            g1[2][2]=t2;
            b1[2][2]=t3;

            
            }
       
            
           else if(m1==14)
            {
                for(int x = 5; x < 8; x++){
                   r1[0][x]=0;
                   r1[1][x]=0;
 
                   g1[0][x]=0;
                   g1[1][x]=0;
                   b1[0][x]=0;
                   b1[1][x]=0;

                }
                   double t=r1[2][2];
                   double t2=g1[2][2];
                   double t3=b1[2][2];
                   
             for(int y=4;y<8;y++)
             {
        
                 r1[y][1]=0;
            
                 g1[y][1]=0;
                 b1[y][1]=0;
             }
             for(int y=5;y<8;y++)
             {
                  r1[y][0]=0;
                  g1[y][0]=0;
                  b1[y][0]=0;
            
             }
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
         
                   
                   }                 
                   }
            r1[2][2]=t;
            g1[2][2]=t2;
            b1[2][2]=t3;
           
            
            }
            
           else if(m1==15)
            {
                for(int x = 5; x < 8; x++){
                    
                         r1[0][x]=0;
                         g1[0][x]=0;
                         b1[0][x]=0;
                    }      
                for(int x = 4; x < 8; x++){
                        
                  
                  
                   r1[1][x]=0;
                  
                   g1[1][x]=0;
                  
                   b1[1][x]=0;

                    }
                   double t=r1[2][2];
                   double t2=g1[2][2];
                   double t3=b1[2][2];
                   
             for(int y=4;y<8;y++)
             {
        
                 r1[y][1]=0;
        
                 g1[y][1]=0;
        
                 b1[y][1]=0;
            
             }
             for(int y=5;y<8;y++)
             {
                  r1[y][0]=0;
                  g1[y][0]=0;
                  b1[y][0]=0;
            
             }
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   
                   }                 
                   }
            r1[2][2]=t;
            g1[2][2]=t2;
            b1[2][2]=t3;

            
            }
            
           else if(m1==16)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                
                }
                    for(int x = 4; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;

                    }
                   double t=r1[2][2];
                   double t2=g1[2][2];
                   double t3=b1[2][2];
                   
             for(int y=4;y<8;y++)
             {
        
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
             for(int y=5;y<8;y++)
             {
                  r1[y][0]=0;
                  g1[y][0]=0;
                  b1[y][0]=0;
            
             }
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;

                   }                 
                   }
            r1[2][2]=t;
            g1[2][2]=t2;
            b1[2][2]=t3;

            
            }
            
            else if(m1==17)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                
                }
                    for(int x = 5; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;

                    }
                   double t=r1[2][2];
                   double t2=g1[2][2];
                   double t3=b1[2][2];
                   
             for(int y=4;y<8;y++)
             {
        
                 r1[y][1]=0;
        
                 g1[y][1]=0;
        
                 b1[y][1]=0;
            
             }
             for(int y=5;y<8;y++)
             {
                  r1[y][0]=0;
                  g1[y][0]=0;
                  b1[y][0]=0;
            
             }
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }
            r1[2][2]=t;
            g1[2][2]=t2;
            b1[2][2]=t3;

            
            }
            
           
            else if(m1==18)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                
                }
                    for(int x = 5; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;

                    }
                   double t=r1[2][2];
                   double t2=r1[2][3];
                   double t3=g1[2][2];
                   double t4=g1[2][3];
                   double t5=b1[2][2];
                   double t6=b1[2][3];
                   
             for(int y=4;y<8;y++)
             {
        
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
             for(int y=5;y<8;y++)
             {
                  r1[y][0]=0;
                  g1[y][0]=0;
                  b1[y][0]=0;
            
             }
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   
                   
                   }                 
                   }

                        r1[2][2]=t;
            r1[2][3]=t2;

                        r1[2][2]=t3;
            r1[2][3]=t4;

                        r1[2][2]=t5;
            r1[2][3]=t6;

                      
}

                        else if(m1==19)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                
                }
                    for(int x = 5; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;

                    }
                   double t=r1[2][2];
                   double t2=r1[2][3];
                   double t3=r1[3][2];
                   double t4=g1[2][2];
                   double t5=g1[2][3];
                   double t6=g1[3][2];
                   double t7=b1[2][2];
                   double t8=b1[2][3];
                   double t9=b1[3][2];

                   
                   
                   for(int y=4;y<8;y++)
             {
        
                 r1[y][1]=0;
        
                 g1[y][1]=0;
        
                 b1[y][1]=0;
            
             }
             for(int y=5;y<8;y++)
             {
                  r1[y][0]=0;
                  g1[y][0]=0;
                  b1[y][0]=0;
            
             }
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
 
                   
                   
                   }                 
                   }

                        r1[2][2]=t;
            r1[2][3]=t2;
r1[3][2]=t3;
            
                                    g1[2][2]=t4;
            g1[2][3]=t5;
g1[3][2]=t6;                        b1[2][2]=t7;
            b1[2][3]=t8;
b1[3][2]=t9;
            
            
            }

                      else if(m1==20)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                
                }
                    for(int x = 5; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;

                    }
                   double t=r1[2][2];
                   double t2=r1[2][3];
                   double t3=r1[3][2];
                   double t4=g1[2][2];
                   double t5=g1[2][3];
                   double t6=g1[3][2];
                   double t7=b1[2][2];
                   double t8=b1[2][3];
                   double t9=b1[3][2];

                   
                   
                   for(int y=5;y<8;y++)
             {
                  r1[y][0]=0;
                 r1[y][1]=0;
                  g1[y][0]=0;
                 g1[y][1]=0;
                  b1[y][0]=0;
                 b1[y][1]=0;
            
             }
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
      
                   
                   
                   }                 
                   }

                        r1[2][2]=t;
            r1[2][3]=t2;
r1[3][2]=t3;
            
                                    g1[2][2]=t4;
            g1[2][3]=t5;
g1[3][2]=t6;                        b1[2][2]=t7;
            b1[2][3]=t8;
b1[3][2]=t9;}

                                  else if(m1==21)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                g1[0][x]=0;
                b1[0][x]=0;
                
                }
                    for(int x = 5; x < 8; x++){
                  
                   r1[1][x]=0;
  g1[1][x]=0;
  b1[1][x]=0;

                    }
                   double t=r1[2][2];
                   double t2=r1[2][3];
                   double t3=r1[3][2];
                   double t4=g1[2][2];
                   double t5=g1[2][3];
                   double t6=g1[3][2];
                   double t7=b1[2][2];
                   double t8=b1[2][3];
                   double t9=b1[3][2];

            
                   
                   for(int y=5;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[6][0]=0;
                  r1[7][0]=0;
                  g1[6][0]=0;
                  g1[7][0]=0;
                  b1[6][0]=0;
                  b1[7][0]=0;
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                   g1[y][x]=0;
                   b1[y][x]=0;
                   }                 
                   }

                        r1[2][2]=t;
            r1[2][3]=t2;
r1[3][2]=t3;
            
                                    g1[2][2]=t4;
            g1[2][3]=t5;
g1[3][2]=t6;                        b1[2][2]=t7;
            b1[2][3]=t8;
b1[3][2]=t9;}

                            else if(m1==22)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                
                }
                    for(int x = 5; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;

                    }
                   double t=r1[2][2];
                   double t2=r1[2][3];
                   double t3=r1[3][2];
                   double t4=g1[2][2];
                   double t5=g1[2][3];
                   double t6=g1[3][2];
                   double t7=b1[2][2];
                   double t8=b1[2][3];
                   double t9=b1[3][2];
 for(int y=5;y<8;y++)
             {
                 r1[y][1]=0;
              g1[y][1]=0;
              b1[y][1]=0;
            
             }
                  r1[7][0]=0;
                  g1[7][0]=0;
                  b1[7][0]=0;
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }
                        r1[2][2]=t;
            r1[2][3]=t2;
r1[3][2]=t3;
            
                                    g1[2][2]=t4;
            g1[2][3]=t5;
g1[3][2]=t6;                        b1[2][2]=t7;
            b1[2][3]=t8;
b1[3][2]=t9;}
            
            
                            else if(m1==23)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                
                }
                    for(int x = 5; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;

                    }
                   double t=r1[2][2];
                   double t2=r1[2][3];
                   double t3=r1[3][2];
                   double t4=g1[2][2];
                   double t5=g1[2][3];
                   double t6=g1[3][2];
                   double t7=b1[2][2];
                   double t8=b1[2][3];
                   double t9=b1[3][2];
             for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[7][0]=0;
                  g1[7][0]=0;
                  b1[7][0]=0;
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                   g1[y][x]=0;
                   b1[y][x]=0;
                   }                 
                   }

                r1[2][2]=t;
            r1[2][3]=t2;
r1[3][2]=t3;
            
                                    g1[2][2]=t4;
            g1[2][3]=t5;
g1[3][2]=t6;                        b1[2][2]=t7;
            b1[2][3]=t8;
b1[3][2]=t9;}

    else if(m1==24)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                
                }
                    for(int x = 5; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;

                    }
                   double t=r1[2][2];
                   double t2=r1[2][3];
                   double t3=r1[3][2];
        double t4=r1[4][2];
     
                   double t5=g1[2][2];
                   double t6=g1[2][3];
                   double t7=g1[3][2];
        double t8=g1[4][2];
      
                   double t9=b1[2][2];
                   double t10=b1[2][3];
                   double t11=b1[3][2];
        double t12=b1[4][2];
        
                   for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[7][0]=0;
                  g1[7][0]=0;
                  b1[7][0]=0;
                        
                   for(int x = 2; x < 8; x++){
                   for(int y = 2; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
     
                   
                   }                 
                   }

                        r1[2][2]=t;
            r1[2][3]=t2;
r1[3][2]=t3;
            r1[4][2]=t4;
               g1[2][2]=t5;
            g1[2][3]=t6;
g1[3][2]=t7;
            g1[4][2]=t8;
               b1[2][2]=t9;
            b1[2][3]=t10;
b1[3][2]=t11;
            b1[4][2]=t12;
            
            }

            
    else if(m1==25)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                
                }
                    for(int x = 5; x < 8; x++){
                  
                   r1[1][x]=0;
g1[1][x]=0;
b1[1][x]=0;

                    }

                                    for(int x = 4; x < 8; x++){
                   r1[2][x]=0;
                  g1[2][x]=0;
                  b1[2][x]=0;

                                    }
                    double t=r1[3][3];
                    double t2=g1[3][3];
                    double t3=b1[3][3];
        
                   for(int y=5;y<8;y++)
             {
                 r1[y][2]=0;
                 g1[y][2]=0;
                 b1[y][2]=0;
            
             }
                   
                   
                   for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[7][0]=0;
                  g1[7][0]=0;
                  b1[7][0]=0;
                        
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }

                        r1[3][3]=t;

                        g1[3][3]=t2;

                        b1[3][3]=t3;
            
            }

            
            
            
    else if(m1==26)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                   g1[0][x]=0;
                   b1[0][x]=0;
                
                }
                    for(int x = 5; x < 8; x++){
                  
                   r1[1][x]=0;
                   g1[1][x]=0;
                   b1[1][x]=0;

                    }
                    
                    
                    for(int x = 5; x < 8; x++){
                   r1[2][x]=0;
                   g1[2][x]=0;
                   b1[2][x]=0;

                                    }
                    double t=r1[3][3];
        double t2=g1[3][3];
        double t3=b1[3][3];
        
                   for(int y=5;y<8;y++)
             {
                 r1[y][2]=0;
                 g1[y][2]=0;
                 b1[y][2]=0;
            
             }
                   
                   
                   for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[7][0]=0;
                  g1[7][0]=0;
                  b1[7][0]=0;
                        
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                   g1[y][x]=0;
                   b1[y][x]=0;
                   }                 
                   }

                        r1[3][3]=t;
                        g1[3][3]=t2;
                        b1[3][3]=t3;
            
            }

    else if(m1==27)
            {
                for(int x = 6; x < 8; x++){
                   r1[0][x]=0;
                r1[1][x]=0;
                   g1[0][x]=0;
                g1[1][x]=0;
                   b1[0][x]=0;
                b1[1][x]=0;

                }
                    
                    
                   for(int x = 5; x < 8; x++){
                   r1[2][x]=0;
  g1[2][x]=0;
  b1[2][x]=0;

                                    }
                    double t=r1[3][3];
                    double t2=g1[3][3];
                    double t3=b1[3][3];
        
                   for(int y=5;y<8;y++)
             {
                 r1[y][2]=0;
                 g1[y][2]=0;
                 b1[y][2]=0;
            
             }
                   
                   
                   for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[7][0]=0;
                  g1[7][0]=0;
                  b1[7][0]=0;
                        
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }

                        r1[3][3]=t;
                        g1[3][3]=t2;
                        b1[3][3]=t3;
            
            }
            

                else if(m1==28)
            {
                r1[0][7]=0;
                g1[0][7]=0;
                b1[0][7]=0;

                
                for(int x = 6; x < 8; x++){
                   
                r1[1][x]=0;
  
                g1[1][x]=0;
  
                b1[1][x]=0;

                }
                    
                    
                   for(int x = 5; x < 8; x++){
                   r1[2][x]=0;
                   g1[2][x]=0;
                   b1[2][x]=0;

                                    }
                    double t=r1[3][3];
                    double t2=g1[3][3];
                    double t3=b1[3][3];
        
                   for(int y=5;y<8;y++)
             {
                 r1[y][2]=0;
            
                 g1[y][2]=0;
                 b1[y][2]=0;
             }
                   
                   
                   for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[7][0]=0;
                        
                  g1[7][0]=0;
                  b1[7][0]=0;
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                                       g1[y][x]=0;
                    b1[y][x]=0;
}                 
                   
                   }

                        r1[3][3]=t;
                        g1[3][3]=t2;
                        b1[3][3]=t3;
            
            }

                else if(m1==29)
            {
                
                for(int x = 6; x < 8; x++){
                   
                r1[1][x]=0;
                g1[1][x]=0;
                b1[1][x]=0;

                }
                    
                    
                   for(int x = 5; x < 8; x++){
                   r1[2][x]=0;
  g1[2][x]=0;
  b1[2][x]=0;

                                    }
                    double t=r1[3][3];
                    double t2=g1[3][3];
                    double t3=b1[3][3];
        
                   for(int y=5;y<8;y++)
             {
                 r1[y][2]=0;
                 g1[y][2]=0;
                 b1[y][2]=0;
            
             }
                   
                   
                   for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[7][0]=0;
                  g1[7][0]=0;
                  b1[7][0]=0;
                        
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   
                   }                 
                   }

                        r1[3][3]=t;
                        r1[3][3]=t2;
                        r1[3][3]=t3;
            
            }
             else if(m1==30)
            {
                
                   
                r1[1][7]=0;
                g1[1][7]=0;
                b1[1][7]=0;

                    
                    
                   for(int x = 5; x < 8; x++){
                   r1[2][x]=0;

                   g1[2][x]=0;
  b1[2][x]=0;
                   }
                    double t=r1[3][3];
                    double t2=g1[3][3];
                    double t3=b1[3][3];
        
                   for(int y=5;y<8;y++)
             {
                 r1[y][2]=0;
                 g1[y][2]=0;
                 b1[y][2]=0;
            
             }
                   
                   
                   for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[6][0]=0;
                  g1[6][0]=0;
                  b1[6][0]=0;
                        
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                   g1[y][x]=0;
                   b1[y][x]=0;
                  
                   }                 
                   }

                        r1[3][3]=t;
                g1[3][3]=t2;
                b1[3][3]=t3;
            
            }
            
            else if(m1==31)
            {
                
                   
                r1[1][7]=0;
                g1[1][7]=0;
                b1[1][7]=0;

                    
                    
                   for(int x = 6; x < 8; x++){
                   r1[2][x]=0;
  g1[2][x]=0;
  b1[2][x]=0;

                                    }
                    double t=r1[3][3];
                    double t2=g1[3][3];
                    double t3=b1[3][3];
        
                   for(int y=5;y<8;y++)
             {
                 r1[y][2]=0;
            
             }
                   
                   
                   for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[7][0]=0;
                  g1[7][0]=0;
                  b1[7][0]=0;
                        
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
   
                   
                   }                 
                   }

                        r1[3][3]=t;
                g1[3][3]=t2;
                b1[3][3]=t3;
            
            }
            
            else if(m1==32)
            {
                
                   
                r1[1][7]=0;
                g1[1][7]=0;
                b1[1][7]=0;

                    
                    
                   for(int x = 6; x < 8; x++){
                   r1[2][x]=0;
  g1[2][x]=0;
  b1[2][x]=0;

                                    }
                    double t=r1[3][3];
                    double t3=g1[3][3];
                    double t5=b1[3][3];
        double t2=r1[3][4];
        double t4=g1[3][4];
        double t6=b1[3][4];
        
                   for(int y=5;y<8;y++)
             {
                 r1[y][2]=0;
                 g1[y][2]=0;
                 b1[y][2]=0;
            
             }
                   
                   
                   for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[7][0]=0;
                  g1[7][0]=0;
                  b1[7][0]=0;
                        
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }

                        r1[3][3]=t;
                    r1[3][4]=t2;
                        g1[3][3]=t3;
                    g1[3][4]=t4;
                        b1[3][3]=t5;
                    b1[3][4]=t6;
            
            }
            
            
            else if(m1==33)
            {
                
                   
                r1[1][7]=0;
                g1[1][7]=0;
                b1[1][7]=0;

                    
                    
                   for(int x = 6; x < 8; x++){
                   r1[2][x]=0;

                   g1[2][x]=0;

                   b1[2][x]=0;

                                    }
                    double t=r1[3][3];
        double t2=r1[3][4];
        double t3=r1[4][3];
                   double t4=g1[3][3];
        double t5=g1[3][4];
        double t6=g1[4][3];
                   double t7=b1[3][3];
        double t8=b1[3][4];
        double t9=b1[4][3];
                   
        for(int y=5;y<8;y++)
             {
                 r1[y][2]=0;
                 g1[y][2]=0;
                 b1[y][2]=0;
            
             }
                   
                   
                   for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[7][0]=0;
                  g1[7][0]=0;
                  b1[7][0]=0;
                        
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }

                        r1[3][3]=t;
                    r1[3][4]=t2;
            r1[4][3]=t3;
                        g1[3][3]=t4;
                    g1[3][4]=t5;
            g1[4][3]=t6;
                        b1[3][3]=t7;
                    b1[3][4]=t8;
            b1[4][3]=t9;

            
            }
            
            
            else if(m1==34)
            {
                
                   
                r1[1][7]=0;
                   
                g1[1][7]=0;
                   
                b1[1][7]=0;

                    
                    
                   for(int x = 6; x < 8; x++){
                   r1[2][x]=0;

                   g1[2][x]=0;
         b1[2][x]=0;
                          }
                    double t=r1[3][3];
        double t2=r1[3][4];
        double t3=r1[4][3];
                   double t4=g1[3][3];
        double t5=g1[3][4];
        double t6=g1[4][3];
                   double t7=b1[3][3];
        double t8=b1[3][4];
        double t9=b1[4][3];
                   for(int y=6;y<8;y++)
             {
                 r1[y][2]=0;
                 g1[y][2]=0;
                 b1[y][2]=0;
            
             }
                   
                   
                   for(int y=6;y<8;y++)
             {
                 r1[y][1]=0;
                 g1[y][1]=0;
                 b1[y][1]=0;
            
             }
                  r1[6][0]=0;
                  g1[6][0]=0;
                  b1[6][0]=0;
                        
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }

 r1[3][3]=t;
                    r1[3][4]=t2;
            r1[4][3]=t3;
                        g1[3][3]=t4;
                    g1[3][4]=t5;
            g1[4][3]=t6;
                        b1[3][3]=t7;
                    b1[3][4]=t8;
            b1[4][3]=t9;
            }
            
            else if(m1==35)
            {
                
                   
                r1[1][7]=0;
     
                   
                g1[1][7]=0;
     
                   
                b1[1][7]=0;

                    
                    
                   for(int x = 6; x < 8; x++){
                   r1[2][x]=0;
  g1[2][x]=0;
  b1[2][x]=0;

                                    }
                    double t=r1[3][3];
        double t2=r1[3][4];
        double t3=r1[4][3];
                   double t4=g1[3][3];
        double t5=g1[3][4];
        double t6=g1[4][3];
                   double t7=b1[3][3];
        double t8=b1[3][4];
        double t9=b1[4][3];
                   for(int y=6;y<8;y++)
             {
                 r1[y][2]=0;
                 g1[y][2]=0;
                 b1[y][2]=0;
            
             }
                   
                   
                 r1[7][1]=0;
            
                  r1[7][0]=0;
                 g1[7][1]=0;
            
                  g1[7][0]=0;
                 b1[7][1]=0;
            
                  b1[7][0]=0;
                        
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
       
                   
                   }                 
                   }

 r1[3][3]=t;
                    r1[3][4]=t2;
            r1[4][3]=t3;
                        g1[3][3]=t4;
                    g1[3][4]=t5;
            g1[4][3]=t6;
                        b1[3][3]=t7;
                    b1[3][4]=t8;
            b1[4][3]=t9;
            }
            
            
            else if(m1==36)
            {
                
                   
                r1[1][7]=0;
                   
                g1[1][7]=0;
                   
                b1[1][7]=0;

                    
                    
                   for(int x = 6; x < 8; x++){
                   r1[2][x]=0;
  g1[2][x]=0;
  b1[2][x]=0;

                                    }
                   double t=r1[3][3];
        double t2=r1[3][4];
        double t3=r1[4][3];
                   double t4=g1[3][3];
        double t5=g1[3][4];
        double t6=g1[4][3];
                   double t7=b1[3][3];
        double t8=b1[3][4];
        double t9=b1[4][3];
        for(int y=6;y<8;y++)
             {
                 r1[y][2]=0;
                 g1[y][2]=0;
                 b1[y][2]=0;
            
             }
                   
                   
                 r1[7][1]=0;
              
                 g1[7][1]=0;
                 b1[7][1]=0;
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }

 r1[3][3]=t;
                    r1[3][4]=t2;
            r1[4][3]=t3;
                        g1[3][3]=t4;
                    g1[3][4]=t5;
            g1[4][3]=t6;
                        b1[3][3]=t7;
                    b1[3][4]=t8;
            b1[4][3]=t9;
            }
            
            
            
            
            else if(m1==37)
            {
                
                   
                r1[1][7]=0;
                g1[1][7]=0;
                b1[1][7]=0;

                    
                    
                   for(int x = 6; x < 8; x++){
                   r1[2][x]=0;

                                    }
                    double t=r1[3][3];
        double t2=r1[3][4];
        double t3=r1[4][3];
                   double t4=g1[3][3];
        double t5=g1[3][4];
        double t6=g1[4][3];
                   double t7=b1[3][3];
        double t8=b1[3][4];
        double t9=b1[4][3];
                   for(int y=6;y<8;y++)
             {
                 r1[y][2]=0;
                 g1[y][2]=0;
                 b1[y][2]=0;
            
             }
                   
                   
             
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }

            
 r1[3][3]=t;
                    r1[3][4]=t2;
            r1[4][3]=t3;
                        g1[3][3]=t4;
                    g1[3][4]=t5;
            g1[4][3]=t6;
                        b1[3][3]=t7;
                    b1[3][4]=t8;
            b1[4][3]=t9;
            }
            
            
            else if(m1==38)
            {
                
                   
                r1[1][7]=0;
              g1[1][7]=0;
              b1[1][7]=0;

                    
                    
                   for(int x = 6; x < 8; x++){
                   r1[2][x]=0;
    g1[2][x]=0;
    b1[2][x]=0;

                                    }
                    double t=r1[3][3];
        double t2=r1[3][4];
        double t3=r1[4][3];
                   double t4=g1[3][3];
        double t5=g1[3][4];
        double t6=g1[4][3];
                   double t7=b1[3][3];
        double t8=b1[3][4];
        double t9=b1[4][3];
            
                 r1[7][2]=0;
                 g1[7][2]=0;
                 b1[7][2]=0;
            
             
                   
                   
             
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }

 r1[3][3]=t;
                    r1[3][4]=t2;
            r1[4][3]=t3;
                        g1[3][3]=t4;
                    g1[3][4]=t5;
            g1[4][3]=t6;
                        b1[3][3]=t7;
                    b1[3][4]=t8;
            b1[4][3]=t9;
        
        
            }

            else if(m1==39)
            {
                
                   
                r1[1][7]=0;
        g1[1][7]=0;
        b1[1][7]=0;

                    
                    
                   for(int x = 6; x < 8; x++){
                   r1[2][x]=0;
          g1[2][x]=0;
          b1[2][x]=0;

                                    }
                    double t=r1[3][3];
        double t2=r1[3][4];
        double t3=r1[4][3];
                   double t4=g1[3][3];
        double t5=g1[3][4];
        double t6=g1[4][3];
                   double t7=b1[3][3];
        double t8=b1[3][4];
        double t9=b1[4][3];
           
                 r1[7][2]=0;
                 g1[7][2]=0;
                 b1[7][2]=0;
            
             
                   
                   
             
                   for(int x = 3; x < 8; x++){
                   for(int y = 3; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }

 r1[3][3]=t;
                    r1[3][4]=t2;
            r1[4][3]=t3;
                        g1[3][3]=t4;
                    g1[3][4]=t5;
            g1[4][3]=t6;
                        b1[3][3]=t7;
                    b1[3][4]=t8;
            b1[4][3]=t9;
        
            }
    
            else if(m1==40)
            {
                
                   
                r1[1][7]=0;

                g1[1][7]=0;
                b1[1][7]=0;
                    
                    
                   for(int x = 6; x < 8; x++){
                   r1[2][x]=0;
  g1[2][x]=0;
  b1[2][x]=0;

                                    }
              r1[6][3]=0;    
        r1[7][3]=0;
        r1[7][2]=0;
        r1[3][5]=0;          
        r1[3][6]=0;          
        r1[3][7]=0;          
              g1[6][3]=0;    
        g1[7][3]=0;
        g1[7][2]=0;
        g1[3][5]=0;          
        g1[3][6]=0;          
        g1[3][7]=0;          
              b1[6][3]=0;    
        b1[7][3]=0;
        b1[7][2]=0;
        b1[3][5]=0;          
        b1[3][6]=0;          
        b1[3][7]=0;          
        
        double t=r1[4][4];
            
        double t2=g1[4][4];
        double t3=b1[4][4];
             
                   
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                   g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }

        r1[4][4]=t;
        g1[4][4]=t;
        b1[4][4]=t;
            
            }
    
            
            else if(m1==41)
            {
                
                   
                r1[1][7]=0;
                g1[1][7]=0;
                b1[1][7]=0;
                    
                   for(int x = 6; x < 8; x++){
                   r1[2][x]=0;
g1[2][x]=0;
b1[2][x]=0;

                                    }
              r1[6][3]=0;    
        r1[7][3]=0;
        r1[7][2]=0;
              r1[3][6]=0;    
        r1[3][7]=0;
              g1[6][3]=0;    
        g1[7][3]=0;
        g1[7][2]=0;
              g1[3][6]=0;    
        g1[3][7]=0;
              b1[6][3]=0;    
        b1[7][3]=0;
        b1[7][2]=0;
              b1[3][6]=0;    
        b1[3][7]=0;
            
             double t=r1[4][4];
             double t2=g1[4][4];
             double t3=b1[4][4];
        
                   
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   
                   }                 
                   }

            r1[4][4]=t;
            g1[4][4]=t2;
            b1[4][4]=t3;
         
            }
    
            else if(m1==42)
            {
                
                   
                r1[1][7]=0;

                    
                    
                   r1[2][7]=0;

              r1[6][3]=0;    
        r1[7][3]=0;
        r1[7][2]=0;
              r1[3][6]=0;    
        r1[3][7]=0;
            
                   
                g1[1][7]=0;

                    
                    
                   g1[2][7]=0;

              g1[6][3]=0;    
        g1[7][3]=0;
        g1[7][2]=0;
              g1[3][6]=0;    
        g1[3][7]=0;
            
                   
                b1[1][7]=0;

                    
                    
                   b1[2][7]=0;

              b1[6][3]=0;    
        b1[7][3]=0;
        b1[7][2]=0;
              b1[3][6]=0;    
        b1[3][7]=0;
            
             double t=r1[4][4];
        
             double t2=g1[4][4];
             double t3=b1[4][4];
                   
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
 
                   }                 
                   }
r1[4][4]=t;
g1[4][4]=t2;
b1[4][4]=t3;
            
            } 
            
                        else if(m1==43)
            {
                
                   
               

double t=r1[4][4];
                            
                    
                   r1[2][7]=0;

              r1[6][3]=0;    
        r1[7][3]=0;
        r1[7][2]=0;
              r1[3][6]=0;    
        r1[3][7]=0;
            
double t2=g1[4][4];
                            
                    
                   g1[2][7]=0;

              g1[6][3]=0;    
        g1[7][3]=0;
        g1[7][2]=0;
              g1[3][6]=0;    
        g1[3][7]=0;
double t3=b1[4][4];
                            
        b1[2][7]=0;

        b1[6][3]=0;    
        b1[7][3]=0;
        b1[7][2]=0;
        b1[3][6]=0;    
        b1[3][7]=0;
             
                   
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }

r1[4][4]=t;
g1[4][4]=t2;
b1[4][4]=t3;
                    
            } 
            
         else if(m1==44)
            {
                
                   
               

                    
                    
              r1[6][3]=0;    
        r1[7][3]=0;
        r1[7][2]=0;
              r1[3][6]=0;    
        r1[3][7]=0;
           double t=r1[4][4];
         
             g1[6][3]=0;    
        g1[7][3]=0;
        g1[7][2]=0;
              g1[3][6]=0;    
        g1[3][7]=0;
           double t2=g1[4][4];
             b1[6][3]=0;    
        b1[7][3]=0;
        b1[7][2]=0;
              b1[3][6]=0;    
        b1[3][7]=0;
           double t3=b1[4][4];
            
                   
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                   g1[y][x]=0;
                   b1[y][x]=0;
                   }                 
                   }
  r1[4][4]=t;
           g1[4][4]=t2;
         
           b1[4][4]=t3;
         
            } 
            
         else if(m1==45)
            {
                
                   
               

                    
                    
              r1[6][3]=0;    
        r1[7][3]=0;
        r1[7][2]=0;
        r1[3][7]=0;
              g1[6][3]=0;    
        g1[7][3]=0;
        g1[7][2]=0;
        g1[3][7]=0;
        b1[6][3]=0;    
        b1[7][3]=0;
        b1[7][2]=0;
        b1[3][7]=0;
           double t=r1[4][4];
         double t2=r1[4][4];
         double t3=r1[4][4];
         
             
                   
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }
r1[4][4]=t;
g1[4][4]=t2;
b1[4][4]=t3;
        
            
            } 

         else if(m1==46)
            {
                
                   
               

                    
                    
              r1[6][3]=0;    
        r1[7][3]=0;
        r1[7][2]=0;
        r1[3][7]=0;
double t=r1[4][4];
      double t2=r1[4][5];
                      
              g1[6][3]=0;    
        g1[7][3]=0;
        g1[7][2]=0;
        g1[3][7]=0;
double t3=g1[4][4];
      double t4=g1[4][5];
             
              b1[6][3]=0;    
        b1[7][3]=0;
        b1[7][2]=0;
        b1[3][7]=0;
double t5=b1[4][4];
      double t6=b1[4][5];
                   
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
           
                   }                 
                   }
r1[4][4]=t;
        r1[4][5]=t2;
        
g1[4][4]=t3;
        g1[4][5]=t4;
b1[4][4]=t5;
        b1[4][5]=t6;
            
            } 

            
         else if(m1==47)
            {
                
                   
               

                    
                    
              r1[6][3]=0;    
        r1[7][3]=0;
        r1[7][2]=0;
        r1[3][7]=0;
double t=r1[4][4];
      double t2=r1[4][5];
double t3=r1[5][4];                      

              g1[6][3]=0;    

        g1[7][3]=0;
        g1[7][2]=0;
        g1[3][7]=0;
double t4=g1[4][4];
      double t5=g1[4][5];
double t6=g1[5][4];                      

              b1[6][3]=0;    

        b1[7][3]=0;
        b1[7][2]=0;
        b1[3][7]=0;
double t7=b1[4][4];
      double t8=b1[4][5];
double t9=b1[5][4];                      
                   


                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }
r1[4][4]=t;
        r1[4][5]=t2;
        r1[5][4]=t3;
        
g1[4][4]=t4;
        g1[4][5]=t5;
        g1[5][4]=t6;

        b1[4][4]=t7;
        b1[4][5]=t8;
        b1[5][4]=t9;

        
            
            } 
            
            
            
         else if(m1==48)
            {
                    
       r1[7][3]=0;
        r1[7][2]=0;
        r1[3][7]=0;
double t=r1[4][4];
      double t2=r1[4][5];
double t3=r1[5][4];                      
             
        g1[7][3]=0;
        g1[7][2]=0;
        g1[3][7]=0;
double t4=g1[4][4];
      double t5=g1[4][5];
double t6=g1[5][4];                      
        b1[7][3]=0;
        b1[7][2]=0;
        b1[3][7]=0;
double t7=b1[4][4];
      double t8=b1[4][5];
double t9=b1[5][4];                      
                   
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }
r1[4][4]=t;
        r1[4][5]=t2;
        r1[5][4]=t3;
        
g1[4][4]=t4;
        g1[4][5]=t5;
        g1[5][4]=t6;

        b1[4][4]=t7;
        b1[4][5]=t8;
        b1[5][4]=t9;

        
        
        
        
            } 
             else if(m1==49)
            {
                
                   
               

                    
                  
        r1[7][3]=0;
       
        r1[3][7]=0;
double t=r1[4][4];
      double t2=r1[4][5];
double t3=r1[5][4];                      
        g1[7][3]=0;
       
        g1[3][7]=0;
double t4=g1[4][4];
      double t5=g1[4][5];
double t6=g1[5][4];                      
        b1[7][3]=0;
       
        b1[3][7]=0;
double t7=b1[4][4];
      double t8=b1[4][5];
double t9=b1[5][4];                      
           
                   
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }
r1[4][4]=t;
        r1[4][5]=t2;
        r1[5][4]=t3;
        
g1[4][4]=t4;
        g1[4][5]=t5;
        g1[5][4]=t6;
b1[4][4]=t7;
        b1[4][5]=t8;
        b1[5][4]=t9;
            
            } 
             else if(m1==50)
            {
        r1[3][7]=0;
double t=r1[4][4];
      double t2=r1[4][5];
double t3=r1[5][4];                      
             
        g1[3][7]=0;
double t4=g1[4][4];
      double t5=g1[4][5];
double t6=g1[5][4];                      
                   
        b1[3][7]=0;
double t7=r1[4][4];
      double t8=g1[4][5];
double t9=b1[5][4];                      
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   
                   
                   }                 
                   }
r1[4][4]=t;
        r1[4][5]=t2;
        r1[5][4]=t3;
        
g1[4][4]=t4;
        g1[4][5]=t5;
        g1[5][4]=t6;
b1[4][4]=t7;
        b1[4][5]=t8;
        b1[5][4]=t9;
            
            } 
        
             else if(m1==51)
            {
        r1[3][7]=0;
double t=r1[4][4];
      double t2=r1[4][5];
double t3=r1[5][4];                      
double t4=r1[6][4];                        
                   
        g1[3][7]=0;
double t1=g1[4][4];
      double t12=g1[4][5];
double t13=g1[5][4];                      
double t14=g1[6][4];                        
        b1[3][7]=0;
double t25=b1[4][4];
      double t22=b1[4][5];
double t23=b1[5][4];                      
double t24=b1[6][4];                        
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }
r1[4][4]=t;
        r1[4][5]=t2;
        r1[5][4]=t3;
        r1[6][4]=t4;
        
g1[4][4]=t1;
        g1[4][5]=t12;
        g1[5][4]=t13;
        g1[6][4]=t14;
        
b1[4][4]=t25;
        b1[4][5]=t22;
        b1[5][4]=t23;
        b1[6][4]=t24;
        
            
            } 
       
             else if(m1==52)
            {
        r1[3][7]=0;
double t=r1[4][4];
      double t2=r1[4][5];
double t3=r1[5][4];                      
double t4=r1[6][4];                        
double t5=r1[5][5];                   
        g1[3][7]=0;
double t1=g1[4][4];
      double t12=g1[4][5];
double t13=g1[5][4];                      
double t14=g1[6][4];                        
double t15=g1[5][5];                   
        b1[3][7]=0;
double t26=b1[4][4];
      double t22=r1[4][5];
double t23=r1[5][4];                      
double t24=r1[6][4];                        
double t25=r1[5][5];                   
                   
             
                   for(int x = 4; x < 8; x++){
                   for(int y = 4; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }
r1[4][4]=t;
        r1[4][5]=t2;
        r1[5][4]=t3;
        r1[6][4]=t4;
        r1[5][5]=t5;
g1[4][4]=t1;
        g1[4][5]=t12;
        g1[5][4]=t13;
        g1[6][4]=t14;
        g1[5][5]=t15;
b1[4][4]=t26;
        b1[4][5]=t22;
        b1[5][4]=t23;
        b1[6][4]=t24;
        b1[5][5]=t25;
            
            } 
         
                else if(m1==53)
            {
        r1[3][7]=0;
        r1[4][7]=0;
        r1[7][4]=0;
g1[3][7]=0;
        g1[4][7]=0;
        g1[7][4]=0;
b1[3][7]=0;
        b1[4][7]=0;
        b1[7][4]=0;

double t1=r1[5][5];                   
double t2=g1[5][5];  double t3=b1[5][5];                     
             
                   for(int x = 5; x < 8; x++){
                   for(int y = 5; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   
                   }                 
                   }
        r1[5][5]=t1;
        g1[5][5]=t2;
        b1[5][5]=t3;
            
            } 
         
            
             else if(m1==54)
            {
        
        r1[4][7]=0;
        r1[7][4]=0;
        g1[4][7]=0;
        g1[7][4]=0;
        b1[4][7]=0;
        b1[7][4]=0;

double t1=r1[5][5];                   
double t2=g1[5][5];                   
double t3=b1[5][5];                   
                   
             
                   for(int x = 5; x < 8; x++){
                   for(int y = 5; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
           
                   
                   }                 
                   }
        r1[5][5]=t1;
        g1[5][5]=t2;
        b1[5][5]=t3;
            
            } 
            
             else if(m1==55)
            {
        
        r1[7][4]=0;

double t1=r1[5][5];                   
                   
        g1[7][4]=0;

double t2=g1[5][5];                   
        b1[7][4]=0;

double t3=b1[5][5];                   
             
                   for(int x = 5; x < 8; x++){
                   for(int y = 5; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
           
                   }                 
                   }
        r1[5][5]=t1;
        g1[5][5]=t2;
        b1[5][5]=t3;
            
            } 
            
             else if(m1==56)
            {
        
        r1[7][4]=0;
        g1[7][4]=0;
        b1[7][4]=0;

double t1=r1[5][5];                   
double t2=r1[5][6];                   
double t3=g1[5][5];                   
double t4=g1[5][6];                   
double t5=b1[5][5];                   
double t6=b1[5][6];                   
                   
             
                   for(int x = 5; x < 8; x++){
                   for(int y = 5; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   }                 
                   }
        r1[5][5]=t1;
        r1[5][6]=t2;
        g1[5][5]=t3;
        g1[5][6]=t4;
        b1[5][5]=t5;
        b1[5][6]=t6;
            
            } 
            
            else if(m1==57)
            {
        
        r1[7][4]=0;
g1[7][4]=0;
b1[7][4]=0;

double t1=r1[5][5];                   
double t2=r1[5][6];          
          
double t3=r1[6][5];          
                   
         
double t4=g1[5][5];                   
double t5=g1[5][6];          
          
double t6=g1[6][5];          
         
double t7=b1[5][5];                   
double t8=b1[5][6];          
          
double t9=b1[6][5];          
             
                   for(int x = 5; x < 8; x++){
                   for(int y = 5; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
                   
                   }                 
                   }
        r1[5][5]=t1;
        r1[5][6]=t2;
        r1[6][5]=t3;
        g1[5][5]=t4;
        g1[5][6]=t5;
        g1[6][5]=t6;
        b1[5][5]=t7;
        b1[5][6]=t8;
        b1[6][5]=t9;
            
            } 
            
            
            else if(m1==58)
            {
        

double t1=r1[5][5];                   
double t2=r1[5][6];          
          
double t3=r1[6][5];          
                   
double t4=g1[5][5];                   
double t5=g1[5][6];          
          
double t6=g1[6][5];          
double t7=b1[5][5];                   
double t8=b1[5][6];          
          
double t9=b1[6][5];          
             
                   for(int x = 5; x < 8; x++){
                   for(int y = 5; y <8; y++){
                    r1[y][x]=0;
                    g1[y][x]=0;
                    b1[y][x]=0;
         
                   
                   
                   
                   }                 
                   }
        r1[5][5]=t1;
        r1[5][6]=t2;
        r1[6][5]=t3;
        g1[5][5]=t4;
        g1[5][6]=t5;
        g1[6][5]=t6;
        b1[5][5]=t7;
        b1[5][6]=t8;
        b1[6][5]=t9;
            
            } 
            
            else if(m1==59)
            {
        

double t1=r1[5][5];                   
double t2=r1[5][6];          
          
double t3=r1[6][5];          
double t4=r1[7][5];                            

double t5=g1[5][5];                   
double t6=g1[5][6];          
          
double t7=g1[6][5];          
double t8=g1[7][5];                            
double t9=b1[5][5];                   
double t10=b1[5][6];          
          
double t11=b1[6][5];          
double t12=b1[7][5];                            
             
                   for(int x = 5; x < 8; x++){
                   for(int y = 5; y <8; y++){
                    r1[y][x]=0;
g1[y][x]=0;b1[y][x]=0;
                   }                 
                   }
        r1[5][5]=t1;
        r1[5][6]=t2;
        r1[6][5]=t3;
        r1[7][5]=t4;
       
        
         g1[5][5]=t5;
        g1[5][6]=t6;
        g1[6][5]=t7;
        g1[7][5]=t8;
        
        b1[5][5]=t9;
        b1[5][6]=t10;
        b1[6][5]=t11;
        b1[7][5]=t12;
       
        
        
        
        
        
        
            }
            
            else if(m1==60)
            {
        

            	double t=r1[6][6];
            	double t2=g1[6][6];
            	double t3=b1[6][6];          
            	r1[5][7]=0;
            	g1[5][7]=0;
            	b1[5][7]=0;
             
             
             for(int x = 6; x < 8; x++){
                   for(int y = 6; y <8; y++){
                    r1[y][x]=0;
                   g1[y][x]=0;
                   b1[y][x]=0;
                   
                   
                   
                   }                 
                   }
            r1[6][6]=t;
         g1[6][6]=t2;
         b1[6][6]=t3;
         
            } 
            else if(m1==61)
            {
        
            	double t=r1[6][6];
            	double t2=g1[6][6];
            	double t3=b1[6][6];
                for(int x = 6; x < 8; x++)
                {
                   for(int y = 6; y <8; y++)
                   {
                	   r1[y][x]=0;
                	   g1[y][x]=0;
                	   b1[y][x]=0;                   
                   }                 
                }
                r1[6][6]=t;
                g1[6][6]=t2;
                b1[6][6]=t3; 
            } 
            
              else if(m1==62)
            {
            	  r1[7][6]=0;
            	  r1[7][7]=0;
            	  g1[7][6]=0;
            	  g1[7][7]=0;
            	  b1[7][6]=0;
            	  b1[7][7]=0;  
            } 
             else if(m1==63)
            {
        
            	 r1[7][7]=0;
            	 g1[7][7]=0;
            	 b1[7][7]=0;
           } 

			//Performing IDCT for the Red Channel
			double sum4=0;

			for(int u=0;u<8;u++)
			{
				for(int v=0;v<8;v++)
				{
					r1mod[u][v]=0;
				}
			}

		    

			for(int i=0;i<8;i++)
			{
				for(int x=0;x<8;x++)
				{
		    
					sum4=0;

					for(int u=0;u<8;u++)
					{
						for(int v=0;v<8;v++)
						{
		   
							if(u==0)
							{
								if(v==0)
								{
									sum4=sum4+0.125*(r1[u][v])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*x+1)*3.14*v/16));
		        
								}  
								else if(v!=0)
								{    
									sum4=sum4+0.25*0.707*(r1[u][v])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*x+1)*3.14*v/16));
								}
							}

							else if(u!=0)
							{
								if(v==0)
								{
									sum4=sum4+0.25*0.707*(r1[u][v])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*x+1)*3.14*v/16));
								}
		    
								else if(v!=0)
								{    sum4=sum4+0.25*(r1[u][v])*Math.cos(((2*i+1)*3.14*u)/16)*Math.cos(((2*x+1)*3.14*v/16));

								}
							}
						}
					}
					r1mod[i][x]=sum4;
				}
			}
			
			//Performing IDCT for the Green Channel
			double sum5=0;

			for(int u=0;u<8;u++)
			{
				for(int v=0;v<8;v++)
				{
					g1mod[u][v]=0;
				}
			}

		    

			for(int i=0;i<8;i++)
			{
				for(int x=0;x<8;x++)
				{
		    
					sum5=0;

					for(int u=0;u<8;u++)
					{
						for(int v=0;v<8;v++)
						{
		   
							if(u==0)
							{
								if(v==0)
								{
									sum5=sum5+0.125*(g1[u][v])*Math.cos(((2*i+1)*3.14*u)/16)
											*Math.cos(((2*x+1)*3.14*v/16));
		        
								}  
								else if(v!=0)
								{    
									sum5=sum5+0.25*0.707*(g1[u][v])*Math.cos(((2*i+1)
											*3.14*u)/16)*Math.cos(((2*x+1)*3.14*v/16));
								}
							}

							else if(u!=0)
							{
								if(v==0)
								{
									sum5=sum5+0.25*0.707*(g1[u][v])*Math.cos(((2*i+1)
											*3.14*u)/16)*Math.cos(((2*x+1)*3.14*v/16));
								}
		    
								else if(v!=0)
								{    sum5=sum5+0.25*(g1[u][v])*Math.cos(((2*i+1)*3.14*u)/16)
									*Math.cos(((2*x+1)*3.14*v/16));

								}
							}
						}
					}
					g1mod[i][x]=sum5;
				}
			}
			
			
			//Performing IDCT for the Blue Channel
			double sum6=0;
			for(int u=0;u<8;u++)
			{
				for(int v=0;v<8;v++)
				{
					b1mod[u][v]=0;
				}
			}

		    

			for(int i=0;i<8;i++)
			{
				for(int x=0;x<8;x++)
				{
		    
					sum6=0;

					for(int u=0;u<8;u++)
					{
						for(int v=0;v<8;v++)
						{
		   
							if(u==0)
							{
								if(v==0)
								{
									sum6=sum6+0.125*(b1[u][v])*Math.cos(((2*i+1)*3.14*u)/16)
											*Math.cos(((2*x+1)*3.14*v/16));
		        
								}  
								else if(v!=0)
								{    
									sum6=sum6+0.25*0.707*(b1[u][v])*Math.cos(((2*i+1)
											*3.14*u)/16)*Math.cos(((2*x+1)*3.14*v/16));
								}
							}

							else if(u!=0)
							{
								if(v==0)
								{
									sum6=sum6+0.25*0.707*(b1[u][v])*Math.cos(((2*i+1)
											*3.14*u)/16)*Math.cos(((2*x+1)*3.14*v/16));
								}
		    
								else if(v!=0)
								{    sum6=sum6+0.25*(b1[u][v])*Math.cos(((2*i+1)*3.14*u)/16)
									*Math.cos(((2*x+1)*3.14*v/16));

								}
							}
						}
					}
					b1mod[i][x]=sum6;
				}
			}
			


			

			for(int q=0;q<8;q++)
			{
    
				indexx=8*(j+k*width); 
				for(int w=0;w<8;w++)
				{ 
					redmod[indexx+width*q]=r1[q][w];
					red1mod[indexx+width*q]=r1mod[q][w];
					greenmod[indexx+width*q]=g1[q][w];
					green1mod[indexx+width*q]=g1mod[q][w];
					bluemod[indexx+width*q]=b1[q][w];
					blue1mod[indexx+width*q]=b1mod[q][w];
					indexx++;
				}
			}



		}
			}


ind=0;


      indexx=0;
      
    
      // Loop to get DCT and IDCT for red colors onto a matrix
      
      for(int i=0;i<height;i++)
      {
          for(int j=0;j<width;j++)
          {      
           rmod[i][j]=((int)red1mod[indexx]);   
           gmod[i][j]=((int)green1mod[indexx]);   
           bmod[i][j]=((int)blue1mod[indexx]);   
     
           indexx++;
          }   
      }
      ind=0;
    
      
      for(int y=0;y<height;y++)
      {
          for(int x=0;x<width;x++)
          {  	
				byte rn = (byte)rmod[y][x];
				byte gn = (byte)gmod[y][x];
				byte bn = (byte)bmod[y][x];
				
				int pixn = 0xff000000 | ((rn & 0xff)  << 16 ) | ((gn & 0xff)<< 8 ) | (bn & 0xff);


							img3.setRGB(x,y,pixn);
                              
          }
      }

}
      			
	} catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  
        frame = new JFrame();
		GridBagLayout gLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gLayout);

		JLabel lbText1 = new JLabel("DCT Transformation");
		lbText1.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lbText2 = new JLabel("DWT Transformation");
		lbText2.setHorizontalAlignment(SwingConstants.CENTER);
		lbIm1 = new JLabel(new ImageIcon(img3));
		lbIm2 = new JLabel(new ImageIcon(img3));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		frame.getContentPane().add(lbText1, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		frame.getContentPane().add(lbText2, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		frame.getContentPane().add(lbIm1, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		frame.getContentPane().add(lbIm2, c);

		frame.pack();
		frame.setVisible(true);
    	}
public static void main(String[] args){
	image3 ren = new image3();
	ren.showIms(args);
}
    
}




