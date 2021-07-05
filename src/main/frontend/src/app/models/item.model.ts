export interface Item {
  id: number | null;
  name: string;
  price: number;
  description: string;
  version: number | null;
}

export interface ItemOne {
  item: Item
}

export interface ItemList {
  items: Item[];
}
