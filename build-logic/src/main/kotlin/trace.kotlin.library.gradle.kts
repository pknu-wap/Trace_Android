import com.example.app.configureKotest
import com.example.app.configureKotlin
import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("jvm")
}

configureKotlin()
configureKotest()
