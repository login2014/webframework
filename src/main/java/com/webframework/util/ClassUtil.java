package com.webframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 */
public class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className){
        return loadClass(className, false);
    }

    /**
     * 加载类
     *
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error("load class failure", e);
            throw new RuntimeException(e);
        }
        return clazz;
    }

    /**
     * 获取包下面所有类集合
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            //获取包路径下的所有资源，
//            clazz.getResource("")获取当前类所在包路径；clazz.getResource("/")获取class path根目录，在一般java项目中就是src下的目录路径
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
//                        将两个空格转换为一个空格
                        String packagePath = url.getPath().replace("%20", " ");
//                        添加类到java集合中
                        addClass(classSet, packagePath, packageName);
//                        判断是否是jar包
                    } else if (protocol.equals("jar")) {
//                        若是jar包则用jar协议的url链接打开
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
//                        jar文件=JarFile，jar中的class文件=JarEntry
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
//                                获取jar文件中的所有entity，即所有class文件
                                Enumeration<JarEntry> jarFileEnumeration = jarFile.entries();
                                while (jarFileEnumeration.hasMoreElements()) {
//                                    遍历循环JarEntry，即class文件
                                    JarEntry jarEntity = jarFileEnumeration.nextElement();
                                    String jarEntityName = jarEntity.getName();
                                    if (jarEntityName.endsWith(".class")) {
                                        String className = jarEntityName.substring(0, jarEntityName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("get class set failure", e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> clazz = loadClass(className, false);
        classSet.add(clazz);
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, final String packageName) {
        final File[] files = new File(packagePath).listFiles(
//                增加文件过滤器
                new FileFilter() {
                    public boolean accept(File pathname) {
//                        若该资源路径是.class或文件夹，则返回true
                        return (pathname.isFile() && pathname.getName().endsWith(".class")) || pathname.isDirectory();
                    }
                });
        for (File file : files) {
            String fileName = file.getName();
//            判断路径是否是文件
            if (file.isFile()) {
                //若是文件则将路径替换为"."+文件名后加入到classSet中
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
//                若是文件夹则将文件夹名称添加到新路径加，循环调用本方法
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packagePath + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }
}
