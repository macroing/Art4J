Img4J
=====
Img4J is an image processing library for Java.

Getting Started
---------------
To clone this repository and build the project using Apache Ant, you can type the following in Git Bash.

```bash
git clone https://github.com/macroing/Img4J.git
cd Img4J
ant
```

Supported Features
------------------
 - `org.macroing.img4j.color` provides the Color API.
 - `org.macroing.img4j.data` provides the Data API.
 - `org.macroing.img4j.geometry` provides the Geometry API.
 - `org.macroing.img4j.geometry.shape` provides the Geometry Shape API.
 - `org.macroing.img4j.image` provides the Image API.
 - `org.macroing.img4j.kernel` provides the Kernel API.
 - `org.macroing.img4j.pixel` provides the Pixel API.
 - `org.macroing.img4j.utility` provides the Utility API.

Documentation
-------------
The documentation for this library can be found in the Javadocs that are generated when building it.

Library
-------
The following table describes the different APIs and their current status in the library.

| Name               | Javadoc | Unit Test | Package                           |
| ------------------ | ------- | --------- | --------------------------------- |
| Color API          | 100.0%  | 100.0%    | org.macroing.img4j.color          |
| Data API           | 100.0%  | 100.0%    | org.macroing.img4j.data           |
| Geometry API       | 100.0%  | 100.0%    | org.macroing.img4j.geometry       |
| Geometry Shape API | 100.0%  | 100.0%    | org.macroing.img4j.geometry.shape |
| Image API          | 100.0%  |  15.0%    | org.macroing.img4j.image          |
| Kernel API         | 100.0%  | 100.0%    | org.macroing.img4j.kernel         |
| Pixel API          | 100.0%  | 100.0%    | org.macroing.img4j.pixel          |
| Utility API        | 100.0%  | 100.0%    | org.macroing.img4j.utility        |

Dependencies
------------
 - [Java 8](http://www.java.com).

Note
----
This library has not reached version 1.0.0 and been released to the public yet. Therefore, you can expect that backward incompatible changes are likely to occur between commits. When this library reaches version 1.0.0, it will be tagged and available on the "releases" page. At that point, backward incompatible changes should only occur when a new major release is made.