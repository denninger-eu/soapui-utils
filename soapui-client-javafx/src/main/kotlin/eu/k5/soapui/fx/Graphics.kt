package eu.k5.soapui.fx

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.io.InputStream


enum class Graphics(file: String) {
    ADD("list-add-3.png"),
    REMOVE("list-remove-3.png"),
    REFRESH("view-refresh-4.png");


    val image = read(file)


    private fun read(file: String): ImageView {
        var input: InputStream? = javaClass.getResourceAsStream("/graphics/$file")
        if (input == null) {

        }
        return input.use {

            val image = Image(input)
            return ImageView(image)
        }
    }
}