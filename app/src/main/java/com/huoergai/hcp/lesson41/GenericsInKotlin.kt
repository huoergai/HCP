package com.huoergai.hcp.lesson41

/**
 * Generics in Kotlin
 *
 * Kotlin     Java
 * out        ? extends
 * in         ? super
 */
class GenericsInKotlin {

    fun demo() {

        val sweetOranges: Container<out SweetOrange> = Container()
        // x sweetOranges.add(SugarSweetOrange())
        val sweetOrange: SweetOrange = sweetOranges.get(0)
        val orange: Orange = sweetOranges.get(0)

        val sweetCitruses: Container<in SweetOrange> = Container()
        // x val sweetCitrus:SweetOrange = sweetCitruses.get(0)
        // x sweetCitruses.add(Orange())
        sweetCitruses.add(SweetOrange())
        sweetCitruses.add(SugarSweetOrange())

    }

}