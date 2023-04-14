Art4J
=====
Art4J is an image processing library for Java.

This project will supersede the old [Image4J](https://github.com/macroing/Image4J) project and the Image API in the [Dayflower](https://github.com/macroing/Dayflower) project.

Getting Started
---------------
To clone this repository and build the project using Apache Ant, you can type the following in Git Bash.

```bash
git clone https://github.com/macroing/Art4J.git
cd Art4J
ant
```

Supported Features
------------------
 - `org.macroing.art4j.color` provides the Color API.
 - `org.macroing.art4j.curve` provides the Curve API.
 - `org.macroing.art4j.data` provides the Data API.
 - `org.macroing.art4j.filter` provides the Filter API.
 - `org.macroing.art4j.image` provides the Image API.
 - `org.macroing.art4j.kernel` provides the Kernel API.
 - `org.macroing.art4j.noise` provides the Noise API.
 - `org.macroing.art4j.pixel` provides the Pixel API.

Examples
--------
Below follows a few examples that demonstrates various features in Art4J.

#### Blend Over Example
The following example loads one image from a URL and generates one, blends them together and saves the result to your hard drive.
```java
import java.net.MalformedURLException;
import java.net.URL;

import org.macroing.art4j.color.Color3D;
import org.macroing.art4j.color.Color4D;
import org.macroing.art4j.data.DataFactory;
import org.macroing.art4j.image.Image;
import org.macroing.art4j.pixel.Color4DBiPixelOperator;
import org.macroing.art4j.pixel.Color4DPixelOperator;

public class BlendOverExample {
    public static void main(String[] args) throws MalformedURLException {
        //Load the Image by URL:
        Image image = new Image(new URL("https://upload.wikimedia.org/wikipedia/en/7/7d/Lenna_%28test_image%29.png"), DataFactory.forColor4D());
        
        //Fill the Image with an alpha component of 0.5D:
        image.fillD((color, x, y) -> new Color4D(color.r, color.g, color.b, 0.5D));
        
        //Fill the Image by blending it together with another Image that contains a gradient:
        image.fillImageD(Color4DBiPixelOperator.blendOver(), new Image(image.getResolutionX(), image.getResolutionY()).fillD(Color4DPixelOperator.gradient(Color3D.BLACK, Color3D.RED, Color3D.GREEN, Color3D.BLUE, image.getBounds())));
        
        //Save the Image:
        image.save("./generated/example/BlendOver.png");
    }
}
```

Documentation
-------------
The documentation for this library can be found in the Javadocs that are generated when building it.

Library
-------
The following table describes the different APIs and their current status in the library.

| Name               | Javadoc | Unit Test | Package                   |
| ------------------ | ------- | --------- | ------------------------- |
| Color API          | 100.0%  | 100.0%    | org.macroing.art4j.color  |
| Curve API          | 100.0%  | 100.0%    | org.macroing.art4j.curve  |
| Data API           | 100.0%  | 100.0%    | org.macroing.art4j.data   |
| Filter API         | 100.0%  | 100.0%    | org.macroing.art4j.filter |
| Image API          | 100.0%  | 100.0%    | org.macroing.art4j.image  |
| Kernel API         | 100.0%  | 100.0%    | org.macroing.art4j.kernel |
| Noise API          | 100.0%  | 100.0%    | org.macroing.art4j.noise  |
| Pixel API          | 100.0%  | 100.0%    | org.macroing.art4j.pixel  |

Dependencies
------------
 - [Java 8 - 17](http://www.java.com)
 - [Macroing / Java](https://github.com/macroing/Java)
 - [Macroing / Geo4J](https://github.com/macroing/Geo4J)

Note
----
This library has not reached version 1.0.0 and been released to the public yet. Therefore, you can expect that backward incompatible changes are likely to occur between commits. When this library reaches version 1.0.0, it will be tagged and available on the "releases" page. At that point, backward incompatible changes should only occur when a new major release is made.