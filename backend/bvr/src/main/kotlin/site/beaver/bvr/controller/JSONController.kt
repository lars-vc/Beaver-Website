package site.beaver.bvr.controller

import com.google.gson.Gson
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
	fun randomBeaver(): String {
		return Gson().toJson(Beaver("dsfsd","/api/beaver","jldsf"))
	}

	@GetMapping("/all/{beaverId}")
	fun getBeaver(@PathVariable beaverId: String): String{
		val img = JSONController::class.java.getResource("/images/beaverpicture ($beaverId).jpg")
		print(img)
		return Gson().toJson(Beaver(beaverId,"dfsdf","jldsf"))
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
	fun getAll(): String {
	 	return ""
	}
}