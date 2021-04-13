package site.beaver.bvr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BvrApplication

fun main(args: Array<String>) {
	runApplication<BvrApplication>(*args)
}
