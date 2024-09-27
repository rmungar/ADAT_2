package org.example

import java.io.File
import kotlin.math.nextUp
import kotlin.math.roundToInt

class FileReader {



    // funcion que recoge todos los datos del fichero
    fun readFile(file: File): List<String>{
        var contenido: List<String> = emptyList()
        if (file.exists()){
             contenido = file.useLines { it.toList() }
        }
        return contenido
    }

//--------------------------------------------------------------------------------------------------------------------//

    // funcion que extrae los datos y los convierte a una lista de mapas
    fun extraerDatos(contenido: List<String>): List<MutableMap<String, String>> {

        var linea = 1
        val resultado = mutableListOf<MutableMap<String, String>>()
        while (linea < contenido.size){
            val contenidoSplit = contenido[linea].split(";")
            val datosAlumno = mutableMapOf<String,String>()
            var cont = 0
            while (cont < contenidoSplit.size){

                when(cont){
                    0 -> {
                        datosAlumno["Apellidos"] = contenidoSplit[cont]
                    }
                    1 -> {
                        datosAlumno["Nombre"] = contenidoSplit[cont]
                    }
                    2 -> {
                        datosAlumno["Asistencia"] = contenidoSplit[cont].replace("%", "")
                    }
                    3 -> {
                        datosAlumno["Parcial1"] = if (contenidoSplit[cont] != ""){
                            contenidoSplit[cont].replace(",",".")
                        }
                        else{
                            contenidoSplit[cont]
                        }
                    }
                    4 -> {
                        datosAlumno["Parcial2"] = if (contenidoSplit[cont] != ""){
                            contenidoSplit[cont].replace(",",".")
                        }
                        else{
                            contenidoSplit[cont]
                        }

                    }
                    5 -> {
                        datosAlumno["Ordinario1"] = if (contenidoSplit[cont] != ""){
                            contenidoSplit[cont].replace(",",".")
                        }
                        else{
                            contenidoSplit[cont]
                        }
                    }
                    6 -> {
                        datosAlumno["Ordinario2"] = if (contenidoSplit[cont] != ""){
                            contenidoSplit[cont].replace(",",".")
                        }
                        else{
                            contenidoSplit[cont]
                        }
                    }
                    7 -> {
                        datosAlumno["Practicas"] = if (contenidoSplit[cont] != ""){
                            contenidoSplit[cont].replace(",",".")
                        }
                        else{
                            contenidoSplit[cont]
                        }
                    }
                    8 -> {
                        datosAlumno["OrdinarioPracticas"] = if (contenidoSplit[cont] != ""){
                            contenidoSplit[cont].replace(",",".")
                        }
                        else{
                            contenidoSplit[cont]
                        }
                    }
                }
                cont ++
            }
            resultado.add(datosAlumno)
            linea ++
        }
        return resultado.sortedBy { it["Apellidos"] }
    }

//--------------------------------------------------------------------------------------------------------------------//

    // funcion que añade la ultima columna a los datos de los estudiantes
    fun addInfo(datos: List<MutableMap<String, String>>): List<MutableMap<String, String>> {
        datos.forEach {
            alumno ->
            alumno["Final"] = calcularNota(alumno).toString()
        }
        return datos
    }

//--------------------------------------------------------------------------------------------------------------------//

    // funcion que calcula la nota necesaria en la funcion anterior
    fun calcularNota(alumno: Map<String, String>): Float{

        var nota1 = 0.0F
        var nota2 = 0.0F
        var nota3 = 0.0F

    // BUSCAMOS EL VALOR DE NOTA1
        if (alumno["Parcial1"] != "" && alumno["Parcial1"] != null) {
            if (alumno["Ordinario1"] != "" && alumno["Ordinario1"] != null){
                if (alumno["Ordinario1"]!!.toDouble() > alumno["Parcial1"]!!.toDouble()) {
                    nota1 = alumno["Ordinario1"]!!.toFloat()
                }
                else nota1 = alumno["Parcial1"]!!.toFloat()
            }
            else nota1 = alumno["Parcial1"]!!.toFloat()
        }
        if (alumno["Ordinario1"] != "" && alumno["Ordinario1"] != null){
            nota1 = alumno["Ordinario1"]!!.toFloat()
        }
    // BUSCAMOS EL VALOR DE NOTA2
        if (!alumno["Parcial2"].isNullOrEmpty()) {
            if (!alumno["Ordinario2"].isNullOrEmpty()){
                if (alumno["Ordinario2"]!!.toDouble() > alumno["Parcial2"]!!.toDouble()) {
                    nota2 = alumno["Ordinario2"]!!.toFloat()
                }
                else nota2 = alumno["Parcial2"]!!.toFloat()
            }
            else nota2 = alumno["Parcial2"]!!.toFloat()
        }
        if (alumno["Ordinario2"] != "" && alumno["Ordinario2"] != null){
            nota2 = alumno["Ordinario2"]!!.toFloat()
        }
    // BUSCAMOS EL VALOR DE NOTA3
        if (!alumno["Practicas"].isNullOrEmpty()) {
            if (!alumno["OrdinarioPracticas"].isNullOrEmpty()){
                if (alumno["OrdinarioPracticas"]!!.toDouble() > alumno["Practicas"]!!.toDouble()) {
                    nota3 = alumno["OrdinarioPracticas"]!!.toFloat()
                }
                else nota3 = alumno["Practicas"]!!.toFloat()
            }
            else nota3 = alumno["Practicas"]!!.toFloat()
        }
        if (!alumno["OrdinarioPracticas"].isNullOrEmpty()){
            nota3 = alumno["OrdinarioPracticas"]!!.toFloat()
        }

        return ((nota1*0.3)+(nota2*0.3)+(nota3*0.4)).toFloat()
    }

//--------------------------------------------------------------------------------------------------------------------//

    // funcion que distribuye a los alumnos en aprobados y suspensos
    fun aprobSusp(datos: List<MutableMap<String, String>>): Pair<List<Map<String,String>>,List<Map<String,String>>>{

        val aprovados = mutableListOf<Map<String,String>>()
        val suspensos = mutableListOf<Map<String,String>>()


        datos.forEach {
            alumno ->
            var aprovado = true
            if (alumno["Asistencia"]!!.toDouble() < 75) aprovado = false
            if (!alumno["Parcial1"].isNullOrEmpty()){
                if (alumno["Parcial1"]!!.toDouble() < 4){
                    if (!alumno["Ordinario1"].isNullOrEmpty()){
                        if (alumno["Ordinario1"]!!.toDouble() < 4 ){
                            aprovado = false
                        }
                    }
                    else{
                        aprovado = false
                    }
                }

            }
            else{
                if (!alumno["Ordinario1"].isNullOrEmpty()){
                    if (alumno["Ordinario1"]!!.toDouble() < 4 ){
                        aprovado = false
                    }
                }
            }
            if (!alumno["Parcial2"].isNullOrEmpty()){
                if (alumno["Parcial2"]!!.toDouble() < 4){
                    if (alumno["Ordinario2"] != null){
                        if (alumno["Ordinario2"]!!.toDouble() < 4 ){
                            aprovado = false
                        }
                    }
                    else{
                        aprovado = false
                    }
                }

            }
            else{
                if (!alumno["Ordinario2"].isNullOrEmpty()){
                    if (alumno["Ordinario2"]!!.toDouble() < 4 ){
                        aprovado = false
                    }
                }
            }
            if (!alumno["Practicas"].isNullOrEmpty()){
                if (alumno["Practicas"]!!.toDouble() < 4){
                    if (!alumno["OrdinarioPracticas"].isNullOrEmpty()){
                        if (alumno["OrdinarioPracticas"]!!.toDouble() < 4){
                            aprovado = false
                        }
                    }
                    else {
                        aprovado = false
                    }
                }
            }
            else{
                if (!alumno["OrdinarioPracticas"].isNullOrEmpty()){
                    if (alumno["OrdinarioPracticas"]!!.toDouble() < 4){
                        aprovado = false
                    }
                }
            }
            if (alumno["Final"]!!.toDouble() < 5) aprovado = false
            if (aprovado) aprovados.add(alumno)
            else suspensos.add(alumno)
        }


        return Pair(aprovados, suspensos)
    }
}