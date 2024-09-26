package org.example

import java.io.File

class FileReader {



    fun readFile(file: File): List<String>{
        var contenido: List<String> = emptyList()
        if (file.exists()){
             contenido = file.useLines { it.toList() }
        }
        return contenido
    }


    fun extraerDatos(contenido: List<String>): MutableList<Map<String, Map<String, String>>> {

        var linea = 1
        val resultado = mutableListOf<Map<String, Map<String, String>>>()
        while (linea < contenido.size){
            val contenidoSplit = contenido[linea].split(";")
            val alumno = mutableMapOf<String,Map<String, String>>()
            val datos = mutableMapOf<String,String>()
            var cont = 0
            while (cont < contenidoSplit.size){

                when(cont){
                    0 -> {
                        datos["Apellidos"] = contenidoSplit[cont]
                    }
                    1 -> {
                        datos["Nombre"] = contenidoSplit[cont]
                    }
                    2 -> {
                        datos["Asistencia"] = contenidoSplit[cont]
                    }
                    3 -> {
                        datos["Parcial1"] = contenidoSplit[cont]
                    }
                    4 -> {
                        datos["Parcial2"] = contenidoSplit[cont]
                    }
                    5 -> {
                        datos["Ordinario1"] = contenidoSplit[cont]
                    }
                    6 -> {
                        datos["Ordinario2"] = contenidoSplit[cont]
                    }
                    7 -> {
                        datos["Practicas"] = contenidoSplit[cont]
                    }
                    8 -> {
                        datos["OrdinarioPracticas"] = contenidoSplit[cont]
                    }
                }
                alumno[contenidoSplit[cont]] = datos
                cont ++
            }
            resultado.add(alumno)
            linea ++
        }

        return resultado
    }


    //fun extraerDatos(contenido: List<String>): MutableList<Map<String, List<String>>> {
//
    //    var linea = 1
    //    val datos = mutableListOf<Map<String,List<String>>>()
    //    while (linea < contenido.size){
    //        var cont = 0
    //        val contenidoLinea = contenido[linea].split(";")
    //        var nombreEstudiante = ""
    //        val listaCalificaciones = mutableListOf<String>()
    //        while (cont < contenidoLinea.size){
    //            if (cont == 0){
    //                nombreEstudiante = contenidoLinea[cont]
    //            }
    //            else{
    //                listaCalificaciones.add(contenidoLinea[cont])
    //            }
    //            cont++
    //        }
    //        datos.add(mapOf(Pair(nombreEstudiante, listaCalificaciones.toList())))
    //        linea++
    //    }
    //    return datos
    //}


    fun addDatos( datos: MutableList<Map<String, List<String>>>){
        datos.forEach {
            linea ->
            linea.values.toMutableList()
        }
    }


    fun guardarDatos(fichero: File, datos: MutableList<Map<String, List<String>>> ){

    }
}