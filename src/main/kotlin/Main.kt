object SimpleGeneticAlgorithm {

//    fun eval(gt: Genotype<BitGene?>?): Int {
//        if (gt != null) {
//            return gt.chromosome
//                    .`as`(BitChromosome::class.java)
//                    .bitCount()
//        }
//        return null;
//    }
//
//    @JvmStatic
//    fun main(args: Array<String>) {
//        val gtf: Factory<Genotype<BitGene?>?>? = Genotype.of(BitChromosome.of(10, 0.5))
//        println("Before the evolution:\n$gtf")
//
//        val engine = Engine
//                .builder<BitGene?, Int>(SimpleGeneticAlgorithm::eval, gtf)
//                .build()
//
//        val result = engine.stream()
//                .limit(500)
//                .collect<Genotype<BitGene>, Any>(EvolutionResult.toBestGenotype())
//
//        println("After the evolution:\n$result")
//
//    }

}