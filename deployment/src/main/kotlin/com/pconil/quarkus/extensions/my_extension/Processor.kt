package com.pconil.quarkus.extensions.my_extension

import com.pconil.quarkus.extensions.my_extension.notifier.MyService
import io.quarkus.arc.deployment.AdditionalBeanBuildItem
import io.quarkus.deployment.annotations.BuildStep
import io.quarkus.deployment.builditem.AdditionalIndexedClassesBuildItem
import io.quarkus.deployment.builditem.FeatureBuildItem


class Processor {

    @BuildStep
    fun feature(): FeatureBuildItem {
        return FeatureBuildItem(FEATURE)
    }

    @BuildStep
    fun registerBeans(): AdditionalBeanBuildItem? {
        return AdditionalBeanBuildItem.builder()
            .addBeanClass(MyService::class.java)
            .build()
    }

    @BuildStep
    fun addAdditionalIndexedClassesBuildItem(): AdditionalIndexedClassesBuildItem {
        val classesToIndex = arrayOf(
            MyClient::class.java.name,
            MyConfig::class.java.name
        )
        return AdditionalIndexedClassesBuildItem(*classesToIndex)
    }

    companion object {
        private const val FEATURE = "my-extension"
    }
}
