package site.beaver.bvr.controller

import com.google.gson.Gson
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.beaver.bvr.model.AllBeavers
import site.beaver.bvr.model.Beaver
import java.io.File
import org.springframework.util.StreamUtils

import org.springframework.core.io.ClassPathResource

import java.io.IOException

import javax.servlet.http.HttpServletResponse

import org.springframework.web.bind.annotation.RequestMethod


@RestController
@RequestMapping("/api/beaver")
class JSONController {
	val preurl = "localhost:8080"

	@GetMapping
	fun default(): HashMap<String, String> {
		return hashMapOf(
			"all" to "$preurl/api/beaver/all",
			"random" to "$preurl/api/beaver/random"
		)
	}

	@GetMapping("/random")
	fun randomBeaver(): Beaver {
		val dir = JSONController::class.java.getResource("/images")
		var str = ""
		File(dir.toURI()).walk().drop(1).shuffled().take(1).forEach {
			str = it.toString()
		}
		val res = str.filter { it.isDigit() }
		return Beaver("$preurl/api/beaver/all", "$preurl/api/beaver/$res", "$preurl/api/beaver/$res/image")
	}

	@GetMapping("/{beaverId}")
	fun getBeaver(@PathVariable beaverId: String): Beaver {
		return Beaver(
			"$preurl/api/beaver/all",
			"$preurl/api/beaver/$beaverId",
			"$preurl/api/beaver/$beaverId/image"
		)
	}

	@GetMapping("/all")
	fun getAll(): AllBeavers {
		val res = mutableListOf<String>()
		val dir = JSONController::class.java.getResource("/images")
		File(dir.toURI()).walk().drop(1).forEach {

			res.add(preurl + "/api/beaver/" + it.name.filter { c -> c.isDigit() })
		}
		return AllBeavers("$preurl/api/beaver", "$preurl/api/beaver/all", res)
	}

	@RequestMapping(
		value = ["/{beaverId}/image"],
		method = [RequestMethod.GET],
		produces = [MediaType.IMAGE_JPEG_VALUE]
	)
	@Throws(
		IOException::class
	)
	fun getImage(response: HttpServletResponse, @PathVariable beaverId: String) {
		val imgFile = ClassPathResource("images/beaverpicture ($beaverId).jpg")
		response.contentType = MediaType.IMAGE_JPEG_VALUE
		StreamUtils.copy(imgFile.inputStream, response.outputStream)
	}
}