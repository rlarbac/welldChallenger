<!-- Programming Test - Pattern Recognition -->
<!-- PROJECT SHIELDS -->
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <h3 align="center">README</h3>

  <p align="center">
     Programming Test - Pattern Recognition
    <br />
</p>


<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
* [Built With](#built-with)
* [Usage](#usage)
** [License](#license)
* [Contact](#contact)


<!-- ABOUT THE PROJECT -->
## About The Project

This project is a programming test proposed by a company.

Given a set of N feature points in the plane, determine every line that contains N or more of the points, and
return all points involved. You should also expose a REST API that will allow the caller to:

Add a point to the space

```sh
POST /point with body { "x": ..., "y": ... }
```

Get all points in the space

```sh
GET /space
```

Get all line segments passing through at least N points. Note that a line segment should be a set of
points.

```sh
GET /lines/{n}
```

Remove all points from the space

```sh
DELETE /space
```

Additionally, I created another service that works like /lines/{n}. However, it returns some additional information such as gradient (slope), constant and line type (Vertical, Horizontal, Upward and Downward).

```sh
GET /lineswithmetadata/{n}
```

An output example:

Scenario:
```sh
POST /point{ "x": 0.0, "y": 2.0 }
POST /point{ "x": 2.0, "y": 2.0 }
POST /point{ "x": 0.0, "y": 0.0 }
POST /point{ "x": 0.0, "y": -2.0 }
GET /lineswithmetadata/2
```

Output JSON (GET /lineswithmetadata/2):
```sh
[
    {
        "gradient": 0.0,
        "constant": 2.0,
        "type": "HORIZONTAL",
        "points": [
            {
                "x": 0.0,
                "y": 2.0
            },
            {
                "x": 2.0,
                "y": 2.0
            }
        ]
    },
    {
        "gradient": 1.0,
        "constant": 0.0,
        "type": "UPWARD",
        "points": [
            {
                "x": 0.0,
                "y": 0.0
            },
            {
                "x": 2.0,
                "y": 2.0
            }
        ]
    },
    {
        "gradient": "NaN",
        "constant": 0.0,
        "type": "VERTICAL",
        "points": [
            {
                "x": 0.0,
                "y": -2.0
            },
            {
                "x": 0.0,
                "y": 0.0
            },
            {
                "x": 0.0,
                "y": 2.0
            }
        ]
    }
]
```

### Built With

* [SpringBoot](https://spring.io/projects/spring-boot)


<!-- USAGE EXAMPLES -->
## Usage

For use the application, you only need to start the SpringBoot application.

```sh
mvnw spring-boot:run
```

And do the service REST request, according to the section About The Project.


<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.


<!-- CONTACT -->
## Contact

Rodrigo Cabral de Azevedo - rlarbac@gmail.com

Project Link: [https://github.com/rlarbac/welldChallenger](https://github.com/rlarbac/welldChallenger)


<!-- MARKDOWN LINKS & IMAGES -->
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/rodrigo-de-azevedo/

