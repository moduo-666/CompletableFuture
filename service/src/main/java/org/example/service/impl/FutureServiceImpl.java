package org.example.service.impl;

import org.example.service.FutureService;
import org.example.service.SpecificTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * CompletableFuture是Java 8 引入的一个类，用于简化异步编程模型。它是Future接口的一个增强版本，提供了更加丰富的功能和更加灵活的用法。
 * CompletableFuture允许开发者以一种声明式和链式的方式编写异步代码，这样可以提高代码的可读性和可维护性
 *
 *
 */
@Service
public class FutureServiceImpl implements FutureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureServiceImpl.class);

    @Autowired
    @Qualifier("customizeExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private SpecificTaskService specificTaskService;

    /**
     * CompletableFuture创建方式总结；
     * CompletableFuture获取结果
     */
    public void newFuture() {
        String threadName = Thread.currentThread().getName();
        /*-------------------------CompletableFuture创建方式总结-------------------------------------------------*/

        //1.1 构造函数创建，直接new一个新的CompletableFuture，这时的CompletableFuture还没有任何结果
        CompletableFuture<String> future = new CompletableFuture<>();
        //1.2 你需要手动调用complete方法来设置返回结果result
        future.complete(specificTaskService.specificTask(threadName, 1));

        //2.1 使用completedFuture()方法
        CompletableFuture<String> future2 = CompletableFuture.completedFuture(specificTaskService.specificTask(threadName, 1));

        //3.1 使用supplyAsync(), 使用这个静态方法会创建一个新的CompletableFuture，这个CompletableFuture会在后台线程执行一个任务，并在任务完成后返回结果
        // supplyAsync()方法有两个重载版本，它们的区别在于是否提供一个Executor参数
        //因此，在实际开发中，我们区分不同的业务，会给业务指定线程池
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> specificTaskService.specificTask(threadName, 1));
        CompletableFuture<String> future3ByAppoint = CompletableFuture.supplyAsync(() -> specificTaskService.specificTask(threadName, 2), threadPoolExecutor);

        //4.1 使用runAsync(), 它与supplyAsync()都是静态方法，但是它不会返回执行结果。
        CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> specificTaskService.specificTask(threadName, 1));
        CompletableFuture<Void> future4ByAppoint = CompletableFuture.runAsync(() -> specificTaskService.specificTaskByException(), threadPoolExecutor);

        /*-------------------------CompletableFuture获取结果-------------------------------------------------*/

        //1.1 get()方法，用于获取异步线程的结果，如果异步操作未完成，get()方法会阻塞当前线程直到操作完成，
        // 如果异步线程操作失败，get()方法会抛出ExecutionException来包装原始的异常。如果当前线程在等待期间被中断，它会抛出InterruptedException
        try {
            future4ByAppoint.get();
            String name = Thread.currentThread().getName();
            long id = Thread.currentThread().getId();
            LOGGER.info("当前线程名称：{}, 当前线程id:{}" ,name, id);
        }catch (InterruptedException e){
            //重置中断状态
            Thread.currentThread().interrupt();
        }catch (ExecutionException e){
            //处理异步操作中的异常
        }

        //2.1 join()方法，与get()类似,也可用于获取CompletableFuture的结果。不同之处在于，join()不会抛出InterruptedException。
        //如果当前线程在等待期间被中断，join() 会将中断状态设置回线程，但不抛出异常。
        //join()可以视为get()的非抛出中断版本的替代方法
        future4.join();
        //总结：get() 方法会抛出经检查的异常，可被捕获，自定义处理或者直接抛出。
        //而 join() 会抛出未经检查的异常。
        //3.1 isDone()方法，用于检查CompletableFuture是否已经完成。它返回一个布尔值，如果异常操作已完成，则返回true，反正返回false。
        //这个方法是非阻塞的，可以用轮询异步操作的完成状态。
        CompletableFuture<Void> future5 = CompletableFuture.runAsync(() ->
                specificTaskService.specificTask(threadName, 2), threadPoolExecutor);
        boolean isCompleted = future5.isDone();
        if(isCompleted){
            System.out.println("future5.success");
        }else{
            System.out.println("future5.ing");
        }

        //总结：get()与join()都会异步操作完成并返回结果，但get()在等待期间线程被中断是会抛出异常，而join不会。
        //isDone()用于检查异步操作是否完成，而不等待操作完成，适用于非阻塞的轮询场景
    }
}
