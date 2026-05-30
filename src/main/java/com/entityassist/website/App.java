package com.entityassist.website;

import com.jwebmp.core.base.angular.client.annotations.references.NgImportReference;
import com.jwebmp.core.base.angular.client.annotations.structures.NgField;
import com.jwebmp.core.base.angular.client.annotations.angular.NgDataType;
import com.jwebmp.core.base.angular.client.annotations.structures.NgSignal;
import com.jwebmp.core.base.angular.client.services.interfaces.INgDataType;

@NgDataType(NgDataType.DataTypeClass.Class)
@NgSignal(value = "true", type = "boolean", referenceName = "useGradle")
@NgImportReference(value = "Injectable", reference = "@angular/core")
@NgImportReference(value = "inject", reference = "@angular/core", onParent = true, onSelf = false)
@NgField(value = "app: App = inject(App);", onParent = true, onSelf = false)
public class App implements INgDataType<App> {
    @Override
    public String renderBeforeClass() {
        return "@Injectable({ providedIn: 'root' })\n";
    }
}
