package site.beaver.bvr.controller

import com.google.gson.Gson
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.beaver.bvr.model.AllBeavers
import site.beaver.bvr.model.Beaver
import java.io.File

@RestController
@RequestMapping("/api/beaver")
class JSONController {

	@GetMapping
	fun default(): String{
		return Gson().toJson(
			hashMapOf<String,String>(
				"all" to "/api/beaver/all",
				"random" to "/api/beaver/random"
			))
	}

	@GetMapping("/random")
	fun randomBeaver(): Beaver {
		return Beaver("/api/beaver/all", "/api/beaver/all/X", "")
	}

	@GetMapping("/all/{beaverId}")
	fun getBeaver(@PathVariable beaverId: String): Beaver{
		val img = JSONController::class.java.getResource("/images/beaverpicture ($beaverId).jpg")
		print(img)
		return Beaver("/api/beaver/all", "/api/beaver/all/$beaverId", img.toString())
	}

	@GetMapping("/all/{beaverId}/image")
	fun getBeaverImage(@PathVariable beaverId: String): String{
		val img = JSONController::class.java.getResource("/images/beaverpicture ($beaverId).jpg")
		print(img)
		return "<html>" +
				"<body>" +
				"<img src=$img />" +
				"</body>" +
				"</html>"
	}

	@GetMapping("/all")
	fun getAll(): AllBeavers {
		val res = mutableListOf<String>()
		val dir = JSONController::class.java.getResource("/images")
		File(dir.toURI()).walk().forEach{
			res.add(it.name)
		}
		return AllBeavers("/api/beaver", "/api/beaver/all", res)
	}
}