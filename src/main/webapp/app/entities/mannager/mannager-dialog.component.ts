import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mannager } from './mannager.model';
import { MannagerPopupService } from './mannager-popup.service';
import { MannagerService } from './mannager.service';

@Component({
    selector: 'jhi-mannager-dialog',
    templateUrl: './mannager-dialog.component.html'
})
export class MannagerDialogComponent implements OnInit {

    mannager: Mannager;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mannagerService: MannagerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mannager.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mannagerService.update(this.mannager));
        } else {
            this.subscribeToSaveResponse(
                this.mannagerService.create(this.mannager));
        }
    }

    private subscribeToSaveResponse(result: Observable<Mannager>) {
        result.subscribe((res: Mannager) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Mannager) {
        this.eventManager.broadcast({ name: 'mannagerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-mannager-popup',
    template: ''
})
export class MannagerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mannagerPopupService: MannagerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mannagerPopupService
                    .open(MannagerDialogComponent as Component, params['id']);
            } else {
                this.mannagerPopupService
                    .open(MannagerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
