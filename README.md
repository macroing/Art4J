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
 - `org.macroing.art4j.geometry` provides the Geometry API.
 - `org.macroing.art4j.geometry.shape` provides the Geometry Shape API.
 - `org.macroing.art4j.image` provides the Image API.
 - `org.macroing.art4j.kernel` provides the Kernel API.
 - `org.macroing.art4j.noise` provides the Noise API.
 - `org.macroing.art4j.pixel` provides the Pixel API.

Examples
--------
Coming soon...

Documentation
-------------
The documentation for this library can be found in the Javadocs that are generated when building it.

Library
-------
The following table describes the different APIs and their current status in the library.

| Name               | Javadoc | Unit Test | Package                           |
| ------------------ | ------- | --------- | --------------------------------- |
| Color API          | 100.0%  | 100.0%    | org.macroing.art4j.color          |
| Curve API          | 100.0%  | 100.0%    | org.macroing.art4j.curve          |
| Data API           | 100.0%  | 100.0%    | org.macroing.art4j.data           |
| Filter API         | 100.0%  | 100.0%    | org.macroing.art4j.filter         |
| Geometry API       | 100.0%  | 100.0%    | org.macroing.art4j.geometry       |
| Geometry Shape API | 100.0%  | 100.0%    | org.macroing.art4j.geometry.shape |
| Image API          | 100.0%  |  44.7%    | org.macroing.art4j.image          |
| Kernel API         | 100.0%  | 100.0%    | org.macroing.art4j.kernel         |
| Noise API          | 100.0%  | 100.0%    | org.macroing.art4j.noise          |
| Pixel API          | 100.0%  |  23.5%    | org.macroing.art4j.pixel          |

Dependencies
------------
 - [Java 8](http://www.java.com)
 - [Macroing / Java](https://github.com/macroing/Java)

Note
----
This library has not reached version 1.0.0 and been released to the public yet. Therefore, you can expect that backward incompatible changes are likely to occur between commits. When this library reaches version 1.0.0, it will be tagged and available on the "releases" page. At that point, backward incompatible changes should only occur when a new major release is made.