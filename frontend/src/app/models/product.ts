export interface Product {
    id?: number;
    name: string;
    price: number;
    qtt: number;
    description: string; 
    menusIncluded?: any[]; // Adjust the type as needed
    combosIncluded?: any[];
    isEdit?: boolean;
}


export const ProductColumns = [
    {
      key: 'name',
      type: 'text',
      label: 'Nome',
      required: true,
    },
    {
      key: 'price',
      type: 'number',
      label: 'Preço',
      required: true,
    },
    {
      key: 'qtt',
      type: 'number',
      label: 'Quantidade',
    },
    {
      key: 'description',
      type: 'text',
      label: 'Descrição',
    },
    {
      key: 'isEdit',
      type: 'isEdit',
      label: '',
    },
  ];
