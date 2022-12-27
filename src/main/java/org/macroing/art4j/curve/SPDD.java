/**
 * Copyright 2022 - 2023 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.art4j.
 * 
 * org.macroing.art4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.art4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.art4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.art4j.curve;

final class SPDD {
	public static final double[] AG_ETA = {1.519000D, 1.496000D, 1.432500D, 1.323000D, 1.142062D, 0.932000D, 0.719062D, 0.526000D, 0.388125D, 0.294000D, 0.253313D, 0.238000D, 0.221438D, 0.209000D, 0.194813D, 0.186000D, 0.192063D, 0.200000D, 0.198063D, 0.192000D, 0.182000D, 0.173000D, 0.172625D, 0.173000D, 0.166688D, 0.160000D, 0.158500D, 0.157000D, 0.151063D, 0.144000D, 0.137313D, 0.132000D, 0.130250D, 0.130000D, 0.129938D, 0.130000D, 0.130063D, 0.129000D, 0.124375D, 0.120000D, 0.119313D, 0.121000D, 0.125500D, 0.131000D, 0.136125D, 0.140000D, 0.140063D, 0.140000D, 0.144313D, 0.148000D, 0.145875D, 0.143000D, 0.142563D, 0.145000D, 0.151938D, 0.163000D};
	public static final double[] AG_K = {1.080000D, 0.882000D, 0.761063D, 0.647000D, 0.550875D, 0.504000D, 0.554375D, 0.663000D, 0.818563D, 0.986000D, 1.120687D, 1.240000D, 1.345250D, 1.440000D, 1.533750D, 1.610000D, 1.641875D, 1.670000D, 1.735000D, 1.810000D, 1.878750D, 1.950000D, 2.029375D, 2.110000D, 2.186250D, 2.260000D, 2.329375D, 2.400000D, 2.478750D, 2.560000D, 2.640000D, 2.720000D, 2.798125D, 2.880000D, 2.973750D, 3.070000D, 3.159375D, 3.250000D, 3.348125D, 3.450000D, 3.553750D, 3.660000D, 3.766250D, 3.880000D, 4.010625D, 4.150000D, 4.293125D, 4.440000D, 4.586250D, 4.740000D, 4.908125D, 5.090000D, 5.288750D, 5.500000D, 5.720624D, 5.950000D};
	public static final double[] AG_WAVELENGTH = {298.757050D, 302.400421D, 306.133759D, 309.960449D, 313.884003D, 317.908142D, 322.036835D, 326.274139D, 330.624481D, 335.092377D, 339.682678D, 344.400482D, 349.251221D, 354.240509D, 359.374420D, 364.659332D, 370.102020D, 375.709625D, 381.489777D, 387.450562D, 393.600555D, 399.948975D, 406.505493D, 413.280579D, 420.285339D, 427.531647D, 435.032196D, 442.800629D, 450.851563D, 459.200653D, 467.864838D, 476.862213D, 486.212463D, 495.936707D, 506.057861D, 516.600769D, 527.592224D, 539.061646D, 551.040771D, 563.564453D, 576.670593D, 590.400818D, 604.800842D, 619.920898D, 635.816284D, 652.548279D, 670.184753D, 688.800964D, 708.481018D, 729.318665D, 751.419250D, 774.901123D, 799.897949D, 826.561157D, 855.063293D, 885.601257D};
	public static final double[] AL_ETA = {0.273375D, 0.280000D, 0.286813D, 0.294000D, 0.301875D, 0.310000D, 0.317875D, 0.326000D, 0.334750D, 0.344000D, 0.353813D, 0.364000D, 0.374375D, 0.385000D, 0.395750D, 0.407000D, 0.419125D, 0.432000D, 0.445688D, 0.460000D, 0.474688D, 0.490000D, 0.506188D, 0.523000D, 0.540063D, 0.558000D, 0.577313D, 0.598000D, 0.620313D, 0.644000D, 0.668625D, 0.695000D, 0.723750D, 0.755000D, 0.789000D, 0.826000D, 0.867000D, 0.912000D, 0.963000D, 1.020000D, 1.080000D, 1.150000D, 1.220000D, 1.300000D, 1.390000D, 1.490000D, 1.600000D, 1.740000D, 1.910000D, 2.140000D, 2.410000D, 2.630000D, 2.800000D, 2.740000D, 2.580000D, 2.240000D};
	public static final double[] AL_K = {3.593750D, 3.640000D, 3.689375D, 3.740000D, 3.789375D, 3.840000D, 3.894375D, 3.950000D, 4.005000D, 4.060000D, 4.113750D, 4.170000D, 4.233750D, 4.300000D, 4.365000D, 4.430000D, 4.493750D, 4.560000D, 4.633750D, 4.710000D, 4.784375D, 4.860000D, 4.938125D, 5.020000D, 5.108750D, 5.200000D, 5.290000D, 5.380000D, 5.480000D, 5.580000D, 5.690000D, 5.800000D, 5.915000D, 6.030000D, 6.150000D, 6.280000D, 6.420000D, 6.550000D, 6.700000D, 6.850000D, 7.000000D, 7.150000D, 7.310000D, 7.480000D, 7.650000D, 7.820000D, 8.010000D, 8.210000D, 8.390000D, 8.570000D, 8.620000D, 8.600000D, 8.450000D, 8.310000D, 8.210000D, 8.210000D};
	public static final double[] AL_WAVELENGTH = {298.757050D, 302.400421D, 306.133759D, 309.960449D, 313.884003D, 317.908142D, 322.036835D, 326.274139D, 330.624481D, 335.092377D, 339.682678D, 344.400482D, 349.251221D, 354.240509D, 359.374420D, 364.659332D, 370.102020D, 375.709625D, 381.489777D, 387.450562D, 393.600555D, 399.948975D, 406.505493D, 413.280579D, 420.285339D, 427.531647D, 435.032196D, 442.800629D, 450.851563D, 459.200653D, 467.864838D, 476.862213D, 486.212463D, 495.936707D, 506.057861D, 516.600769D, 527.592224D, 539.061646D, 551.040771D, 563.564453D, 576.670593D, 590.400818D, 604.800842D, 619.920898D, 635.816284D, 652.548279D, 670.184753D, 688.800964D, 708.481018D, 729.318665D, 751.419250D, 774.901123D, 799.897949D, 826.561157D, 855.063293D, 885.601257D};
	public static final double[] AU_ETA = {1.795000D, 1.812000D, 1.822625D, 1.830000D, 1.837125D, 1.840000D, 1.834250D, 1.824000D, 1.812000D, 1.798000D, 1.782000D, 1.766000D, 1.752500D, 1.740000D, 1.727625D, 1.716000D, 1.705875D, 1.696000D, 1.684750D, 1.674000D, 1.666000D, 1.658000D, 1.647250D, 1.636000D, 1.628000D, 1.616000D, 1.596250D, 1.562000D, 1.502125D, 1.426000D, 1.345875D, 1.242000D, 1.086750D, 0.916000D, 0.754500D, 0.608000D, 0.491750D, 0.402000D, 0.345500D, 0.306000D, 0.267625D, 0.236000D, 0.212375D, 0.194000D, 0.177750D, 0.166000D, 0.161000D, 0.160000D, 0.160875D, 0.164000D, 0.169500D, 0.176000D, 0.181375D, 0.188000D, 0.198125D, 0.210000D};
	public static final double[] AU_K = {1.920375D, 1.920000D, 1.918875D, 1.916000D, 1.911375D, 1.904000D, 1.891375D, 1.878000D, 1.868250D, 1.860000D, 1.851750D, 1.846000D, 1.845250D, 1.848000D, 1.852375D, 1.862000D, 1.883000D, 1.906000D, 1.922500D, 1.936000D, 1.947750D, 1.956000D, 1.959375D, 1.958000D, 1.951375D, 1.940000D, 1.924500D, 1.904000D, 1.875875D, 1.846000D, 1.814625D, 1.796000D, 1.797375D, 1.840000D, 1.956500D, 2.120000D, 2.326250D, 2.540000D, 2.730625D, 2.880000D, 2.940625D, 2.970000D, 3.015000D, 3.060000D, 3.070000D, 3.150000D, 3.445812D, 3.800000D, 4.087687D, 4.357000D, 4.610188D, 4.860000D, 5.125813D, 5.390000D, 5.631250D, 5.880000D};
	public static final double[] AU_WAVELENGTH = {298.757050D, 302.400421D, 306.133759D, 309.960449D, 313.884003D, 317.908142D, 322.036835D, 326.274139D, 330.624481D, 335.092377D, 339.682678D, 344.400482D, 349.251221D, 354.240509D, 359.374420D, 364.659332D, 370.102020D, 375.709625D, 381.489777D, 387.450562D, 393.600555D, 399.948975D, 406.505493D, 413.280579D, 420.285339D, 427.531647D, 435.032196D, 442.800629D, 450.851563D, 459.200653D, 467.864838D, 476.862213D, 486.212463D, 495.936707D, 506.057861D, 516.600769D, 527.592224D, 539.061646D, 551.040771D, 563.564453D, 576.670593D, 590.400818D, 604.800842D, 619.920898D, 635.816284D, 652.548279D, 670.184753D, 688.800964D, 708.481018D, 729.318665D, 751.419250D, 774.901123D, 799.897949D, 826.561157D, 855.063293D, 885.601257D};
	public static final double[] BE_ETA = {2.470000D, 2.550000D, 2.640000D, 2.730000D, 2.840000D, 2.950000D, 3.070000D, 3.190000D, 3.300000D, 3.390000D, 3.460000D, 3.470000D, 3.440000D, 3.350000D};
	public static final double[] BE_K = {3.080000D, 3.080000D, 3.080000D, 3.100000D, 3.120000D, 3.140000D, 3.160000D, 3.160000D, 3.180000D, 3.170000D, 3.180000D, 3.230000D, 3.350000D, 3.550000D};
	public static final double[] BE_WAVELENGTH = {310.000000D, 326.300018D, 344.399994D, 364.600006D, 387.399994D, 413.300018D, 442.800018D, 476.800018D, 516.600037D, 563.500000D, 619.900024D, 688.799988D, 774.900024D, 885.600037D};
	public static final double[] CR_ETA = {0.980000D, 1.020000D, 1.060000D, 1.120000D, 1.180000D, 1.260000D, 1.330000D, 1.390000D, 1.430000D, 1.440000D, 1.480000D, 1.540000D, 1.650000D, 1.800000D, 1.990000D, 2.220000D, 2.490000D, 2.750000D, 2.980000D, 3.180000D, 3.340000D, 3.480000D, 3.840000D, 4.230000D, 4.270000D, 4.310000D, 4.330000D, 4.380000D};
	public static final double[] CR_K = {2.670000D, 2.760000D, 2.850000D, 2.950000D, 3.040000D, 3.120000D, 3.180000D, 3.240000D, 3.310000D, 3.400000D, 3.540000D, 3.710000D, 3.890000D, 4.060000D, 4.220000D, 4.360000D, 4.440000D, 4.460000D, 4.450000D, 4.410000D, 4.380000D, 4.360000D, 4.370000D, 4.340000D, 4.330000D, 4.320000D, 4.320000D, 4.310000D};
	public static final double[] CR_WAVELENGTH = {300.194000D, 307.643005D, 316.276001D, 323.708008D, 333.279999D, 341.542999D, 351.217987D, 362.514984D, 372.312012D, 385.031006D, 396.102020D, 409.175018D, 424.589020D, 438.092010D, 455.808990D, 471.406982D, 490.040009D, 512.314026D, 532.102966D, 558.468018D, 582.066040D, 610.739014D, 700.452026D, 815.658020D, 826.533020D, 849.178040D, 860.971985D, 885.570984D};
	public static final double[] CU_ETA = {1.400313D, 1.380000D, 1.358438D, 1.340000D, 1.329063D, 1.325000D, 1.332500D, 1.340000D, 1.334375D, 1.325000D, 1.317812D, 1.310000D, 1.300313D, 1.290000D, 1.281563D, 1.270000D, 1.249062D, 1.225000D, 1.200000D, 1.180000D, 1.174375D, 1.175000D, 1.177500D, 1.180000D, 1.178125D, 1.175000D, 1.172812D, 1.170000D, 1.165312D, 1.160000D, 1.155312D, 1.150000D, 1.142812D, 1.135000D, 1.131562D, 1.120000D, 1.092437D, 1.040000D, 0.950375D, 0.826000D, 0.645875D, 0.468000D, 0.351250D, 0.272000D, 0.230813D, 0.214000D, 0.209250D, 0.213000D, 0.216250D, 0.223000D, 0.236500D, 0.250000D, 0.254188D, 0.260000D, 0.280000D, 0.300000D};
	public static final double[] CU_K = {1.662125D, 1.687000D, 1.703313D, 1.720000D, 1.744563D, 1.770000D, 1.791625D, 1.810000D, 1.822125D, 1.834000D, 1.851750D, 1.872000D, 1.894250D, 1.916000D, 1.931688D, 1.950000D, 1.972438D, 2.015000D, 2.121562D, 2.210000D, 2.177188D, 2.130000D, 2.160063D, 2.210000D, 2.249938D, 2.289000D, 2.326000D, 2.362000D, 2.397625D, 2.433000D, 2.469187D, 2.504000D, 2.535875D, 2.564000D, 2.589625D, 2.605000D, 2.595562D, 2.583000D, 2.576500D, 2.599000D, 2.678062D, 2.809000D, 3.010750D, 3.240000D, 3.458187D, 3.670000D, 3.863125D, 4.050000D, 4.239563D, 4.430000D, 4.619563D, 4.817000D, 5.034125D, 5.260000D, 5.485625D, 5.717000D};
	public static final double[] CU_WAVELENGTH = {298.757050D, 302.400421D, 306.133759D, 309.960449D, 313.884003D, 317.908142D, 322.036835D, 326.274139D, 330.624481D, 335.092377D, 339.682678D, 344.400482D, 349.251221D, 354.240509D, 359.374420D, 364.659332D, 370.102020D, 375.709625D, 381.489777D, 387.450562D, 393.600555D, 399.948975D, 406.505493D, 413.280579D, 420.285339D, 427.531647D, 435.032196D, 442.800629D, 450.851563D, 459.200653D, 467.864838D, 476.862213D, 486.212463D, 495.936707D, 506.057861D, 516.600769D, 527.592224D, 539.061646D, 551.040771D, 563.564453D, 576.670593D, 590.400818D, 604.800842D, 619.920898D, 635.816284D, 652.548279D, 670.184753D, 688.800964D, 708.481018D, 729.318665D, 751.419250D, 774.901123D, 799.897949D, 826.561157D, 855.063293D, 885.601257D};
	public static final double[] HG_ETA = {0.542000D, 0.589000D, 0.644000D, 0.713000D, 0.798000D, 0.898000D, 1.027000D, 1.186000D, 1.384000D, 1.620000D, 1.910000D, 2.284000D, 2.746000D, 3.324000D};
	public static final double[] HG_K = {2.502000D, 2.665000D, 2.860000D, 3.074000D, 3.294000D, 3.538000D, 3.802000D, 4.090000D, 4.407000D, 4.751000D, 5.150000D, 5.582000D, 6.054000D, 6.558000D};
	public static final double[] HG_WAVELENGTH = {309.950012D, 326.263000D, 344.389008D, 364.647003D, 387.437988D, 413.266998D, 442.785980D, 476.846008D, 516.583008D, 563.545044D, 619.900024D, 688.778015D, 774.875000D, 885.570984D};
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private SPDD() {
		
	}
}