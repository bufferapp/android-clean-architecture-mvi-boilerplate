package org.buffer.android.boilerplate.presentation.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer layers
 *
 * @param <V> the view model output type
 * @param <D> the domain model input type
 */
interface Mapper<out V, in D> {

    /**
     * Function to map a domain model to a view model
     * @param type input domain model
     * @return <V> the respective view model type
     */
    fun mapToView(type: D): V

}
