Nucleus ![Build](https://github.com/TeamBR-Modding/Nucleus/workflows/Build/badge.svg)<a href='https://minecraft.curseforge.com/projects/nucleus'><img src='http://cf.way2muchnoise.eu/full_nucleus_downloads.svg'><img src='http://cf.way2muchnoise.eu/versions/nucleus_latest.svg'></a>
======

Library used by pauljoda mods. Contains base class helpers for energy/inventories/fluid and a simple GUI system.  

Other credits include:
- Dyonovan
- Buzz135


Adding to Project
=================

Add the following code to build.gradle. You will need to have two files in the root of the project named "USERNAME" and "ACCESS_TOKEN" that have your github username and an access token set with the "read:packages" permissions. Do not upload this publically, just used to access the package. You may also have to add information for the JEI maven if you haven't already. You can alternatively setup local build variables for this information as well, follow GitHub documentaion for more info about adding GitHub package repositories: https://docs.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-gradle-for-use-with-github-packages#installing-a-package

```
repositories {
    maven {
        name "Nucleus"
        url 'https://maven.pkg.github.com/pauljoda/Nucleus'
        credentials {
            // Must create files in the same directory with this info, one per file. DO NOT UPLOAD
            username new File("USERNAME").text
            password new File("ACCESS_TOKEN").text
        }
    }
}

dependencies {
    compile group: "com.pauljoda", name: "nucleus", version: "+", classifier: "dev"
}
```


License
=======
<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" property="dct:title">Nucleus</span> by <a xmlns:cc="http://creativecommons.org/ns#" href="http://minecraft.curseforge.com/projects/bookshelf-api-library" property="cc:attributionName" rel="cc:attributionURL">pauljoda</a> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License</a>.<br />Based on a work at <a xmlns:dct="http://purl.org/dc/terms/" href="https://github.com/TeamBR-Modding/Bookshelf" rel="dct:source">https://github.com/TeamBR-Modding/Bookshelf</a>.
