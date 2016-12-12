<?php

$file_path ="Imagenes/";

$success = file_put_contents($file_path . "afile", "This is a test");

if($success === false) {
    echo "Couldnt write file";
} else {
    echo "Wrote $success bytes";
}

?>