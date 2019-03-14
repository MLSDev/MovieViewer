package di

import dagger.Component
import tests.MovieDetailsViewModelTest
import javax.inject.Singleton

@Singleton
@Component(modules = [TestModule::class])
interface TestComponent {
    fun inject(target: MovieDetailsViewModelTest)
}