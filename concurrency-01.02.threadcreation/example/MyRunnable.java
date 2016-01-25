package example;

class MyRunnable implements Runnable
{
	

    public void run()
    {
        for (;;)
            System.out.print("-");
    }

    public static void main(String[] argv)
    {
        Runnable r = new MyRunnable();
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();

        for(;;)  System.out.print("|");
    }
}