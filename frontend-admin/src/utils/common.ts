import {
  JavaCollectionModel, JavaPagedModel, SimplifiedCollectionModel, SimplifiedPagedModel,
} from 'types/common';

export const collectionModelSimplifier = <T>(collectionModel: JavaCollectionModel<T>): SimplifiedCollectionModel<T> => ({
  list: collectionModel?._embedded && Object.values(collectionModel._embedded)?.[0],
});

export const pagedModelSimplifier = <T>(pagedModel: JavaPagedModel<T>): SimplifiedPagedModel<T> => ({
  list: collectionModelSimplifier<T>(pagedModel).list,
  page: {
    pageSize: pagedModel.page.size,
    total: pagedModel.page.totalElements,
    current: pagedModel.page.number,
  },
});

export default {
  collectionModelSimplifier,
  pagedModelSimplifier,
};
