import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Mannager } from './mannager.model';
import { MannagerPopupService } from './mannager-popup.service';
import { MannagerService } from './mannager.service';

@Component({
    selector: 'jhi-mannager-delete-dialog',
    templateUrl: './mannager-delete-dialog.component.html'
})
export class MannagerDeleteDialogComponent {

    mannager: Mannager;

    constructor(
        private mannagerService: MannagerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mannagerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mannagerListModification',
                content: 'Deleted an mannager'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mannager-delete-popup',
    template: ''
})
export class MannagerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mannagerPopupService: MannagerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mannagerPopupService
                .open(MannagerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
