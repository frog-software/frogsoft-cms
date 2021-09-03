export interface JavaCollectionModel<T> {
  _embedded: {
    [key: string]: T[]
  },
  _links: {
    [key: string]: {
      href: string
    }
  },
}

export interface JavaPagedModel<T> extends JavaCollectionModel<T> {
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number,
  },
}

export interface SimplifiedCollectionModel<T> {
  list: T[]
}

export interface SimplifiedPagedModel<T> extends SimplifiedCollectionModel<T> {
  page: {
    pageSize: number,
    total: number,
    current: number,
  },
}
