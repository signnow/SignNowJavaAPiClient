package com.signnow.library.facades;

public interface ServiceProvider {
    Documents documentsService();

    Templates templatesService();

    DocumentGroups documentGroupsService();
}
