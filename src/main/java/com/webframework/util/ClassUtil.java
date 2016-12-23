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
     * ��ȡ�������
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
     * ������
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
     * ��ȡ�����������༯��
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            //��ȡ��·���µ�������Դ��
//            clazz.getResource("")��ȡ��ǰ�����ڰ�·����clazz.getResource("/")��ȡclass path��Ŀ¼����һ��java��Ŀ�о���src�µ�Ŀ¼·��
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
//                        �������ո�ת��Ϊһ���ո�
                        String packagePath = url.getPath().replace("%20", " ");
//                        ����ൽjava������
                        addClass(classSet, packagePath, packageName);
//                        �ж��Ƿ���jar��
                    } else if (protocol.equals("jar")) {
//                        ����jar������jarЭ���url���Ӵ�
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
//                        jar�ļ�=JarFile��jar�е�class�ļ�=JarEntry
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
//                                ��ȡjar�ļ��е�����entity��������class�ļ�
                                Enumeration<JarEntry> jarFileEnumeration = jarFile.entries();
                                while (jarFileEnumeration.hasMoreElements()) {
//                                    ����ѭ��JarEntry����class�ļ�
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
//                �����ļ�������
                new FileFilter() {
                    public boolean accept(File pathname) {
//                        ������Դ·����.class���ļ��У��򷵻�true
                        return (pathname.isFile() && pathname.getName().endsWith(".class")) || pathname.isDirectory();
                    }
                });
        for (File file : files) {
            String fileName = file.getName();
//            �ж�·���Ƿ����ļ�
            if (file.isFile()) {
                //�����ļ���·���滻Ϊ"."+�ļ�������뵽classSet��
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
//                �����ļ������ļ���������ӵ���·���ӣ�ѭ�����ñ�����
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
