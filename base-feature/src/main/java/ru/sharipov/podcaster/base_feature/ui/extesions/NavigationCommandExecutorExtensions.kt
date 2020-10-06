package ru.sharipov.podcaster.base_feature.ui.extesions

import ru.surfstudio.android.navigation.animation.Animations
import ru.surfstudio.android.navigation.animation.DefaultAnimations
import ru.surfstudio.android.navigation.command.NavigationCommand
import ru.surfstudio.android.navigation.command.activity.Start
import ru.surfstudio.android.navigation.command.dialog.Dismiss
import ru.surfstudio.android.navigation.command.dialog.Show
import ru.surfstudio.android.navigation.command.fragment.Add
import ru.surfstudio.android.navigation.command.fragment.RemoveLast
import ru.surfstudio.android.navigation.executor.NavigationCommandExecutor
import ru.surfstudio.android.navigation.route.activity.ActivityRoute
import ru.surfstudio.android.navigation.route.dialog.DialogRoute
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

fun NavigationCommandExecutor.start(route: ActivityRoute) {
    execute(Start(route))
}

fun NavigationCommandExecutor.replace(route: ActivityRoute) {
    execute(ru.surfstudio.android.navigation.command.activity.Replace(route))
}

fun NavigationCommandExecutor.add(route: FragmentRoute) {
    execute(Add(route))
}

fun NavigationCommandExecutor.replace(
    route: FragmentRoute,
    animations: Animations = DefaultAnimations.fragment,
    tag: String = ""
) {
    execute(ru.surfstudio.android.navigation.command.fragment.Replace(route, animations, tag))
}

fun NavigationCommandExecutor.show(route: DialogRoute) {
    execute(Show(route))
}

fun NavigationCommandExecutor.dismiss(route: DialogRoute) {
    execute(Dismiss(route))
}

fun NavigationCommandExecutor.removeLastFragment() {
    execute(RemoveLast())
}

fun NavigationCommandExecutor.execute(vararg commands: NavigationCommand) {
    execute(commands.asList())
}