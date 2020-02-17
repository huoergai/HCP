package com.huoergai.processor;

import com.huoegai.annotation.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class BindProcessor extends AbstractProcessor {
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("processing...");

        for (Element e : roundEnv.getRootElements()) {
            String packageName = e.getEnclosingElement().toString();
            String className = e.getSimpleName().toString();
            ClassName genClassName = ClassName.get(packageName, className + "Binding");
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(packageName, className), "activity");
            boolean hasBinding = false;
            for (Element enclosedE : e.getEnclosedElements()) {
                if (enclosedE.getKind() == ElementKind.FIELD) {
                    BindView bindView = enclosedE.getAnnotation(BindView.class);
                    if (bindView != null) {
                        hasBinding = true;
                        constructorBuilder.addStatement("activity.$N = activity.findViewById($L)", enclosedE.getSimpleName(), bindView.value());
                    }
                }
            }
            TypeSpec typeSpec = TypeSpec.classBuilder(genClassName)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(constructorBuilder.build())
                    .build();

            if (hasBinding) {
                try {
                    JavaFile.builder(packageName, typeSpec)
                            .build()
                            .writeTo(filer);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }

        /*
        String packageName = "com.huoergai.hcp.lesson40";
        ClassName className = ClassName.get(packageName, "L40ActivityViewBinding");
        TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ClassName.get(packageName, "L40Activity"), "activity")
                        .addStatement("activity.tv = activity.findViewById(R.id.l40_tv)")
                        .build())
                .build();

        try {
            JavaFile.builder(packageName, typeSpec)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        // 生成 TTest class 文件

        /*
        ClassName className = ClassName.get("com.huoergai.hcp.lesson40", "TTest");
        TypeSpec typeSpec = TypeSpec.classBuilder(className).build();

        try {
            JavaFile.builder("com.huoergai.hcp.lesson40", typeSpec)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }
}